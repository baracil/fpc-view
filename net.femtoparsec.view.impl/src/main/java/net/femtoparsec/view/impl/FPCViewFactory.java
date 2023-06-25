package net.femtoparsec.view.impl;

import net.femtoparsec.view.api.View;
import net.femtoparsec.view.api.ViewFactory;

public class FPCViewFactory implements ViewFactory {

  @Override
  public String getName() {
    return "fpc";
  }

  @Override
  public View createView() {
    return new FPCView();
  }

}
