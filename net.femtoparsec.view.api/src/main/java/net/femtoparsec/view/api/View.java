package net.femtoparsec.view.api;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.UnaryOperator;

public interface View {

  List<Layer> getLayers();

  View createLayer(String layerName);

  View mutateLayer(String layerName, UnaryOperator<Layer> mutator);

  default View mutateItem(String layerName, String layerItem, UnaryOperator<LayerItem> mutator) {
    return mutateLayer(layerName, layer -> layer.mutateItem(layerItem, mutator));
  }

  default View addItem(String layerName, LayerItem item) {
    return mutateLayer(layerName, layer -> layer.addItem(item));
  }

  static View create() {
    return ServiceLoader.load(ViewFactory.class)
        .findFirst()
        .orElseThrow(NoViewFactoryImplementation::new)
        .createView();
  }
}
