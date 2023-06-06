package net.femtoparsec.view.impl;

import net.femtoparsec.view.api.Layer;
import net.femtoparsec.view.api.LayerItem;
import org.junit.jupiter.api.Test;

public class TestLayerCreation {

  @Test
  public void name() {
    Layer layer = new FPCLayer("layer");
    final var nbItems = 100;

    for (int i = 0; i < nbItems; i++) {
      layer = layer.addItem(new DummyDrawer(String.valueOf(i)));
    }

    final var start = System.nanoTime();
    final var name = String.valueOf(nbItems/2);
    final var n = 10000;
    for (int i = 0; i < n; i++) {
      layer = layer.mutateDrawer(name,p -> new DummyDrawer(p.name()));
    }
    final var last = (System.nanoTime() - start)*1e-9;
    System.out.println(last/n*1e6);

  }

  private record DummyDrawer(String name) implements LayerItem.Drawer {
  }
}
