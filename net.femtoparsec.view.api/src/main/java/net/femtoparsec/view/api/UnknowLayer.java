package net.femtoparsec.view.api;

import lombok.Getter;

public class UnknowLayer extends ViewException{

  @Getter
  private final String layerName;

  public UnknowLayer(String layerName) {
    super("Could not found layer named '"+layerName+"'");
    this.layerName = layerName;
  }
}
