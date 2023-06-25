package net.femtoparsec.view.api;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ServiceLoader;

public interface LayerRenderer {

  void render(View view, String layerName, Graphics2D graphics2D, double width, double height);

  default void render(View view, String layerName, BufferedImage bufferedImage) {
    final var graphic = bufferedImage.createGraphics();
    try {
      this.render(view, layerName, graphic, bufferedImage.getWidth(), bufferedImage.getHeight());
    } finally {
      graphic.dispose();
    }
  }


  static LayerRenderer create(String name) {
    return ServiceLoader.load(LayerRendererFactory.class)
        .stream()
        .map(ServiceLoader.Provider::get)
        .filter(f -> f.getName().equals(name))
        .findFirst().orElseThrow(() -> new NoViewFactoryImplementation(name))
        .createLayerRender();
  }

  static LayerRenderer create() {
    return create("fpc");
  }


}
