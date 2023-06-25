package net.femtoparsec.view.renderer.impl;

import net.femtoparsec.view.api.LayerRenderer;
import net.femtoparsec.view.api.LayerRendererFactory;

public class FPCLayerRendererFactory implements LayerRendererFactory {

  @Override
  public String getName() {
    return "fpc";
  }

  @Override
  public LayerRenderer createLayerRender() {
    return new FPCLayerRenderer();
  }

}
