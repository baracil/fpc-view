package net.femtoparsec.view.api;

import lombok.Getter;

public class DuplicateLayerItemName extends ViewException {

  @Getter
  private final String layerName;

  @Getter
  private final String layerItemName;

  public DuplicateLayerItemName(String layerName, String layerItemName) {
    super("An item with the name '"+layerItemName+"' exists in the layer '"+layerName+"' already");
    this.layerName = layerName;
    this.layerItemName = layerItemName;
  }
}
