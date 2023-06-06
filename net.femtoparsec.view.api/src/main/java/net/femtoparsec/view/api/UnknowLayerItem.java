package net.femtoparsec.view.api;

import lombok.Getter;

public class UnknowLayerItem extends ViewException{

  @Getter
  private final String layerName;
  @Getter
  private final String itemName;

  public UnknowLayerItem(String layerName, String itemName) {
    super("Could not found item with name '"+itemName+"' in layer named '"+layerName+"'");
    this.layerName = layerName;
    this.itemName = itemName;
  }
}
