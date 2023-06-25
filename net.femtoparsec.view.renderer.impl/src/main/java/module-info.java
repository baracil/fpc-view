import net.femtoparsec.view.api.LayerRendererFactory;
import net.femtoparsec.view.renderer.impl.FPCLayerRendererFactory;

module fpc.renderer.impl {
  requires static lombok;
  requires java.desktop;

  requires fpc.view.api;

  provides LayerRendererFactory with FPCLayerRendererFactory;
}