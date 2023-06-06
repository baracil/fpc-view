package net.femtoparsec.view.impl;

import lombok.Getter;
import lombok.Value;
import net.femtoparsec.view.api.Layer;
import net.femtoparsec.view.api.LayerItem;
import net.femtoparsec.view.api.UnknowLayerItem;
import net.femtoparsec.view.api.ViewException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class FPCLayer implements Layer {

  @Getter
  private final String name;
  @Getter
  private final List<LayerItem> items;

  public FPCLayer(String name) {
    this.name = name;
    this.items = List.of();
  }

  private FPCLayer(String name, List<LayerItem> items) {
    this.name = name;
    this.items = Collections.unmodifiableList(items);
  }

  @Override
  public Layer mutateItem(String itemName, UnaryOperator<LayerItem> mutator) {
    return mutateItem(itemName,mutator,LayerItem.class);
  }

  @Override
  public Layer mutateDrawer(String itemName, UnaryOperator<LayerItem.Drawer> mutator) {
    return mutateItem(itemName,mutator, LayerItem.Drawer.class);
  }

  @Override
  public Layer mutateLayout(String itemName, UnaryOperator<LayerItem.Layout> mutator) {
    return mutateItem(itemName,mutator, LayerItem.Layout.class);
  }

  private <T extends LayerItem> Layer mutateItem(String itemName, UnaryOperator<T> mutator, Class<T> type) {
    final var rawItem = getItem(itemName);
    if (!type.isInstance(rawItem)) {
      throw new ViewException("The item named '"+itemName+"' is not of type '"+type+"'");
    }
    final var item = type.cast(rawItem);
    final var newItem = mutator.apply(item);

    if (newItem == item) {
      return this;
    }

    final List<LayerItem> newItems = new ArrayList<>(items.size());
    for (LayerItem layerItem : this.items) {
      final var l = layerItem.hasName(itemName) ? newItem : layerItem;
      newItems.add(l);
    }
    return new FPCLayer(name, newItems);
  }

  @Override
  public Layer addItem(LayerItem item) {
    final List<LayerItem> newItems = new ArrayList<>(items.size() + 1);
    newItems.addAll(items);
    newItems.add(item);
    return new FPCLayer(name, newItems);
  }

  @Override
  public Layer addItem(LayerItem item, int index) {
    final List<LayerItem> newItems = new ArrayList<>(items.size() + 1);
    newItems.addAll(items);
    newItems.add(item);
    return new FPCLayer(name, newItems);
  }

  @Override
  public Layer removeItem(String itemName) {
    boolean found = false;
    final List<LayerItem> newItems = new ArrayList<>(items.size());
    for (LayerItem newItem : this.items) {
      if (!newItem.hasName(itemName)) {
        newItems.add(newItem);
        found = true;
      }
    }
    return found ? new FPCLayer(name, newItems) : this;
  }

  @Override
  public Layer moveToFront(String itemName) {
    if (items.isEmpty()) {
      throw new UnknowLayerItem(name, itemName);
    }
    if (items.get(items.size() - 1).hasName(itemName)) {
      return this;
    }

    LayerItem itemToMoveToFront = null;
    final List<LayerItem> newItems = new ArrayList<>(items.size());
    for (LayerItem item : this.items) {
      if (item.hasName(itemName)) {
        itemToMoveToFront = item;
      } else {
        newItems.add(item);
      }
    }
    if (itemToMoveToFront == null) {
      throw new UnknowLayerItem(name, itemName);
    }
    newItems.add(itemToMoveToFront);
    return new FPCLayer(name, newItems);
  }

  @Override
  public Layer moveToBack(String itemName) {
    if (items.isEmpty()) {
      throw new UnknowLayerItem(name, itemName);
    }
    if (items.get(0).hasName(itemName)) {
      return this;
    }

    LayerItem itemToMoveToBack = null;
    final List<LayerItem> newItems = new ArrayList<>(items.size());
    newItems.add(null);
    for (LayerItem item : this.items) {
      if (item.hasName(itemName)) {
        itemToMoveToBack = item;
      } else {
        newItems.add(item);
      }
    }
    if (itemToMoveToBack == null) {
      throw new UnknowLayerItem(name, itemName);
    }
    newItems.set(0, itemToMoveToBack);
    return new FPCLayer(name, newItems);
  }

  @Override
  public Layer moveInFrontOf(String targetItemName, String referenceItemName) {
    final var searchResult = findItems(targetItemName,referenceItemName);

    if (searchResult.targetIdx == searchResult.referenceIdx+1) {
      return this;
    }

    final List<LayerItem> newItems = new ArrayList<>(items.size());
    for (int i = 0; i < items.size(); i++) {
      if (searchResult.isTarget(i)) {
        continue;
      }
      newItems.add(items.get(i));
      if (searchResult.isReference(i)) {
        newItems.add(searchResult.target);
      }
    }

    return new FPCLayer(name,newItems);
  }

  @Override
  public Layer moveBehindOf(String targetItemName, String referenceItemName) {
    final var searchResult = findItems(targetItemName,referenceItemName);

    if (searchResult.targetIdx == searchResult.referenceIdx-1) {
      return this;
    }

    final List<LayerItem> newItems = new ArrayList<>(items.size());
    for (int i = 0; i < items.size(); i++) {
      if (searchResult.isTarget(i)) {
        continue;
      }
      if (searchResult.isReference(i)) {
        newItems.add(searchResult.target);
      }
      newItems.add(items.get(i));
    }

    return new FPCLayer(name,newItems);
  }

  private LayerItem getItem(String itemName) {
    for (LayerItem item : items) {
      if (item.hasName(itemName)) {
        return item;
      }
    }
    throw new UnknowLayerItem(name, itemName);
  }

  private SearchResult findItems(String targetName, String referenceName) {
    int targetIdx = -1;
    int referenceIdx = -1;
    LayerItem target = null;
    LayerItem reference = null;
    for (int i = 0; i < items.size(); i++) {
      var item = items.get(i);
      if (item.hasName(targetName)) {
        targetIdx = i;
        target = item;
      } else if (item.hasName(referenceName)) {
        referenceIdx = i;
        reference = item;
      }
    }

    if (targetIdx < 0) {
      throw new UnknowLayerItem(name,targetName);
    }

    if (referenceIdx < 0) {
      throw new UnknowLayerItem(name,referenceName);
    }

    return new SearchResult(targetIdx,referenceIdx,target,reference);
  }

  @Value
  private static class SearchResult {
    int targetIdx;
    int referenceIdx;
    LayerItem target;
    LayerItem reference;

    public boolean isTarget(int index) {
      return this.targetIdx == index;
    }
    public boolean isReference(int index) {
      return this.referenceIdx == index;
    }
  }
}
