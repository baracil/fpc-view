module fpc.view.api {
  requires static lombok;
  requires java.desktop;
  uses net.femtoparsec.view.api.ViewFactory;

  exports net.femtoparsec.view.api;
}