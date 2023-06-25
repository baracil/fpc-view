package net.femtoparsec.view.renderer.impl;

import net.femtoparsec.view.api.*;

import java.awt.*;

public class FPCLayerRenderer implements LayerRenderer {
  @Override
  public void render(View view, String layerName, Graphics2D graphics2D, double width, double height) {
    var layout = new Layout(graphics2D,width,height);

    for (LayerItem item : view.layerItems(layerName)) {
      if (item instanceof LayerItem.Drawer drawer) {
        final var g2 = (Graphics2D)graphics2D.create();
        try {
          drawer.draw(g2, width, height);
        } finally {
          g2.dispose();
        }
      } else if (item instanceof LayerItem.Layouter layouter) {
        layout = layouter.layout(layout);
      } else {
        //Should never happen since LayerItem is sealed. Wait for pattern matching
        //to remove this
        throw new ViewException("Unknown layout item type : "+item.getClass());
      }
    }
  }
}
