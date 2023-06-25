package net.femtoparsec.view.api;

import lombok.Getter;

public class DuplicateLayerName extends ViewException {

  @Getter
  private final String layerName;

  public DuplicateLayerName(String layerName) {
    super("A layer with the name '" + layerName + "' exists in the view already");
    this.layerName = layerName;
  }
}
