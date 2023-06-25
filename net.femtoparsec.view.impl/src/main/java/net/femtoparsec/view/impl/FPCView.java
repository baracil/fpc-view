package net.femtoparsec.view.impl;

import net.femtoparsec.view.api.*;
import net.femtoparsec.view.impl.mutator.*;

import java.util.*;

import static java.util.function.Predicate.not;

public class FPCView implements View {

  private static final LayerItem[] EMPTY_LAYER_ITEM = new LayerItem[0];

  private final List<String> layerNames;
  /**
   * A linkedHashMap could be used instead of keeping the order of the layer
   * in a specific list, but the list must be built when getting the list of layer.
   */
  private final Map<String, LayerItem[]> layers;

  public FPCView() {
    this.layerNames = List.of();
    this.layers = Map.of();
  }

  private FPCView(List<String> layerNames, Map<String, LayerItem[]> layers) {
    this.layerNames = layerNames;
    this.layers = layers;
  }

  @Override
  public View addLayer(String layerName) {
    if (this.layers.isEmpty()) {
      return new FPCView(List.of(layerName), Map.of(layerName, EMPTY_LAYER_ITEM));
    }
    if (this.layers.containsKey(layerName)) {
      throw new DuplicateLayerName(layerName);
    }
    final List<String> newLayerNames = new ArrayList<>(this.layerNames.size() + 1);
    final var newLayers = this.duplicateLayerMap();
    newLayerNames.add(layerName);
    newLayers.put(layerName, EMPTY_LAYER_ITEM);
    return new FPCView(newLayerNames, newLayers);
  }


  @Override
  public List<String> getLayerNames() {
    return this.layerNames;
  }

  @Override
  public List<LayerItem> layerItems(String layerName) {
    final var items = this.layers.get(layerName);
    if (items == null) {
      throw new UnknowLayer(layerName);
    }
    return Collections.unmodifiableList(Arrays.asList(items));
  }

  @Override
  public View removeLayer(String layerName) {
    if (!this.layers.containsKey(layerName)) {
      return this;
    }
    final var newLayerNames = this.layerNames.stream().filter(not(layerName::equals)).toList();
    final var newLayers = this.duplicateLayerMap();
    newLayers.remove(layerName);
    return new FPCView(newLayerNames, newLayers);
  }

  @Override
  public View addItem(String layerName, LayerItem layerItem) {
    return this.mutateLayer(layerName, new AddItemMutator(layerItem));
  }

  @Override
  public View removeItem(String layerName, String itemName) {
    return this.mutateLayer(layerName, new DeleteItemMutator(itemName));
  }

  @Override
  public View moveToFront(String layerName, String itemName) {
    return this.mutateLayer(layerName,new MoveToFrontMutator(itemName));
  }

  @Override
  public View moveToBack(String layerName, String itemName) {
    return this.mutateLayer(layerName,new MoveToBackMutator(itemName));
  }

  @Override
  public View moveInFrontOf(String layerName, String targetItemName, String referenceItemName) {
    return this.mutateLayer(layerName,new MoveInFrontOfMutator(targetItemName,referenceItemName));
  }

  @Override
  public View moveBehind(String layerName, String targetItemName, String referenceItemName) {
    return this.mutateLayer(layerName,new MoveBehindMutator(targetItemName,referenceItemName));
  }

  private Map<String, LayerItem[]> duplicateLayerMap() {
    return new HashMap<>(this.layers);
  }

  private View mutateLayer(String layerName, LayerMutator mutator) {
    final var reference = this.layers.get(layerName);
    if (reference == null) {
      throw new UnknowLayer(layerName);
    }
    try {
      final var mutated = mutator.mutate(reference);
      if (mutated == reference) {
        return this;
      }
      final var newLayers = this.duplicateLayerMap();
      newLayers.put(layerName, mutated);
      return new FPCView(this.layerNames, newLayers);
    } catch (Exception e) {
      throw new ViewException("Fail to mutate layer '"+layerName+"' : "+e.getMessage(),e);
    }
  }
}
