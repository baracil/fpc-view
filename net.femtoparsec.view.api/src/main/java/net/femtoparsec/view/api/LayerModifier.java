package net.femtoparsec.view.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LayerModifier {

  private final @NonNull View view;
  private final @NonNull String layerName;


  public LayerModifier addItem(@NonNull LayerItem item) {
    final var view = this.view.addItem(this.layerName, item);
    return this.withView(view);
  }

  public LayerModifier removeItem(String itemName) {
    return this.withView(this.view.removeItem(this.layerName, itemName));
  }

  public LayerModifier moveToFront(String itemName) {
    return this.withView(this.view.moveToFront(this.layerName, itemName));
  }

  public LayerModifier moveToBack(String itemName) {
    return this.withView(this.view.moveToBack(this.layerName, itemName));
  }

  public LayerModifier moveInFrontOf(String targetItemName, String referenceItemName) {
    return this.withView(this.view.moveInFrontOf(this.layerName, targetItemName, referenceItemName));
  }

  public LayerModifier moveBehind(String targetItemName, String referenceItemName) {
    return this.withView(this.view.moveBehind(this.layerName, targetItemName, referenceItemName));
  }

  private LayerModifier withView(@NonNull View view) {
    if (this.view == view) {
      return this;
    }
    return new LayerModifier(view, this.layerName);
  }

}
