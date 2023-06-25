package net.femtoparsec.view.api;

public interface LayerRendererFactory {

  String getName();

  LayerRenderer createLayerRender();

}
