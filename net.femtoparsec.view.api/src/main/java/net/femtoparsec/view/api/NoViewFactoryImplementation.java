package net.femtoparsec.view.api;

import lombok.Getter;

public class NoViewFactoryImplementation extends ViewException {

  @Getter
  private final String factoryName;

  public NoViewFactoryImplementation(String factoryName) {
    super("Could not find any ViewFactory with name '"+factoryName+"'");
    this.factoryName = factoryName;
  }
}
