package net.femtoparsec.view.api;

import lombok.Getter;

public class DuplicateLayerItemName extends ViewException {

  @Getter
  private final String layerItemName;

  public DuplicateLayerItemName(String layerItemName) {
    super("An item with the name '" + layerItemName + "' exists already");
    this.layerItemName = layerItemName;
  }
}
