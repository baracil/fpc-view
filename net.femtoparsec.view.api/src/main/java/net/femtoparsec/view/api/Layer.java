package net.femtoparsec.view.api;

import java.util.List;
import java.util.function.UnaryOperator;

public interface Layer {

  String getName();


  List<LayerItem> getItems();

  default int getNumberOfItems() {
    return getItems().size();
  }

  Layer mutateItem(String itemName, UnaryOperator<LayerItem> mutator);

  Layer mutateDrawer(String itemName, UnaryOperator<LayerItem.Drawer> mutator);

  Layer mutateLayout(String itemName, UnaryOperator<LayerItem.Layout> mutator);

  Layer addItem(LayerItem item);

  Layer addItem(LayerItem item, int index);

  Layer removeItem(String itemName);

  Layer moveToFront(String itemName);

  Layer moveToBack(String itemName);

  Layer moveInFrontOf(String targetItemName, String referenceItemName);

  Layer moveBehindOf(String targetItemName, String referenceItemName);


  default boolean hasName(String layerName) {
    return getName().equals(layerName);
  }
}
