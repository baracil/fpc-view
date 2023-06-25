module fpc.view.api {
  requires static lombok;
  requires java.desktop;
  uses net.femtoparsec.view.api.ViewFactory;
  uses net.femtoparsec.view.api.LayerRendererFactory;

  exports net.femtoparsec.view.api;
}