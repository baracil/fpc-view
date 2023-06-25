package net.femtoparsec.view.api;

import java.awt.*;

public sealed interface LayerItem permits LayerItem.Drawer, LayerItem.Layouter {

  String getName();

  default boolean hasName(String name) {
    return this.getName().equals(name);
  }


  non-sealed interface Drawer extends LayerItem {

    void draw(Graphics2D graphics2D, double width, double height);

  }

  non-sealed interface Layouter extends LayerItem {

    Layout layout(Layout layout);

  }
}
