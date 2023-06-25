package net.femtoparsec.view.api;

import lombok.Getter;

public class UnknowLayerItem extends ViewException {

  @Getter
  private final String itemName;

  public UnknowLayerItem(String itemName) {
    super("Could not found item with name '" + itemName + "'");
    this.itemName = itemName;
  }
}
