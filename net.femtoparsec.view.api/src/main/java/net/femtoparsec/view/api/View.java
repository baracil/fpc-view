package net.femtoparsec.view.api;

import java.util.List;
import java.util.ServiceLoader;

public interface View {

  List<String> getLayerNames();

  List<LayerItem> layerItems(String layerName);

  View addLayer(String layerName);

  View removeLayer(String layerName);

  View addItem(String layerName, LayerItem layerItem);

  View removeItem(String layerName, String itemName);

  View moveToFront(String layerName, String itemName);

  View moveToBack(String layerName, String itemName);

  View moveInFrontOf(String layerName, String targetItemName, String referenceItemName);

  View moveBehind(String layerName, String targetItemName, String referenceItemName);

  default LayerModifier createModifier(String layerName) {
    return new LayerModifier(this, layerName);
  }

  default Layer getLayer(String layerName) {
    return new Layer(this, layerName);
  }

  static View create(String name) {
    return ServiceLoader.load(ViewFactory.class)
        .stream()
        .map(ServiceLoader.Provider::get)
        .filter(f -> f.getName().equals(name))
        .findFirst().orElseThrow(() -> new NoViewFactoryImplementation(name))
        .createView();
  }

  static View create() {
    return create("fpc");
  }


}
