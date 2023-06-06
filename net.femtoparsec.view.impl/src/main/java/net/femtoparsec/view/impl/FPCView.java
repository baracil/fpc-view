package net.femtoparsec.view.impl;

import lombok.Getter;
import net.femtoparsec.view.api.Layer;
import net.femtoparsec.view.api.LayerItem;
import net.femtoparsec.view.api.UnknowLayer;
import net.femtoparsec.view.api.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class FPCView implements View  {

  @Getter
  private final List<Layer> layers;

  public FPCView() {
    this.layers = List.of();
  }

  private FPCView(List<Layer> layers) {
    this.layers = Collections.unmodifiableList(layers);
  }

  @Override
  public View createLayer(String layerName) {
    if (layers.isEmpty()) {
      return new FPCView(List.of(new FPCLayer(layerName)));
    }
    final List<Layer> newLayers = new ArrayList<>(layers.size()+1);

    newLayers.addAll(layers);
    newLayers.add(new FPCLayer(layerName));

    return new FPCView(newLayers);
  }

  @Override
  public View mutateLayer(String layerName, UnaryOperator<Layer> mutator) {
    final var layer = getLayer(layerName);
    final var newLayer = mutator.apply(layer);
    if (newLayer == layer) {
      return this;
    }

    final List<Layer> newLayers = new ArrayList<>(layers.size());
    for (Layer l : this.layers) {
      if (l == layer) {
        newLayers.add(newLayer);
      } else {
        newLayers.add(l);
      }
    }

    return new FPCView(newLayers);
  }

  @Override
  public View mutateItem(String layerName, String layerItem, UnaryOperator<LayerItem> mutator) {
    return mutateLayer(layerName, l -> l.mutateItem(layerItem,mutator));
  }


  private Layer getLayer(String layerName) {
    for (Layer layer : layers) {
      if  (layer.hasName(layerName)) {
        return layer;
      }
    }
    throw new UnknowLayer(layerName);
  }
}
