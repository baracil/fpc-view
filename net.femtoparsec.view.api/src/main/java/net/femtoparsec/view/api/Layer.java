package net.femtoparsec.view.api;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Layer {

  private final View view;
  private final String layerName;

  public List<LayerItem> layerItems() {
    return this.view.layerItems(this.layerName);
  }

}
