package net.femtoparsec.view.api;

public sealed interface LayerItem permits LayerItem.Drawer, LayerItem.Layout {

  String name();

  default boolean hasName(String name) {
    return name().equals(name);
  }


  non-sealed interface Drawer extends LayerItem {

  }

  non-sealed interface Layout extends LayerItem {

  }
}
