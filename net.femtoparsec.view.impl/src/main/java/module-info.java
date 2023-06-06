import net.femtoparsec.view.api.ViewFactory;
import net.femtoparsec.view.impl.FPCViewFactory;

module fpc.view.impl {
  requires static lombok;
  requires java.desktop;

  requires fpc.view.api;

  provides ViewFactory with FPCViewFactory;
}