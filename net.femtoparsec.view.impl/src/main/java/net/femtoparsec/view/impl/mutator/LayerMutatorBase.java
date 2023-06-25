package net.femtoparsec.view.impl.mutator;

import lombok.RequiredArgsConstructor;
import net.femtoparsec.view.api.LayerItem;
import net.femtoparsec.view.api.UnknowLayerItem;
import net.femtoparsec.view.api.ViewException;
import net.femtoparsec.view.impl.ItemInfo;
import net.femtoparsec.view.impl.LayerMutator;

@RequiredArgsConstructor
public abstract class LayerMutatorBase implements LayerMutator {

  protected ItemInfo<LayerItem> getItemInfo(LayerItem[] items, String itemName) {
    for (int i = 0; i < items.length; i++) {
      var item = items[i];
      if (item.hasName(itemName)) {
        return new ItemInfo<>(item, i);
      }
    }
    throw  new UnknowLayerItem(itemName);
  }

  protected <T extends LayerItem> ItemInfo<T> getItemInfo(LayerItem[] items, String itemName, Class<T> itemType) {
    final var itemInfo = this.getItemInfo(items, itemName);
    return itemInfo
        .cast(itemType)
        .orElseThrow(() -> new ViewException("The item named '" + itemName + " is of type '" + itemInfo.item().getClass() + "' but it has been requested as '" + itemType + "'"));
  }

  protected SearchResult findItems(LayerItem[] items, String targetName, String referenceName) {
    ItemInfo<LayerItem> target = null;
    ItemInfo<LayerItem> reference = null;
    for (int i = 0; i < items.length; i++) {
      var item = items[i];
      if (item.hasName(targetName)) {
        target = new ItemInfo<>(item, i);
        if (reference != null) {
          break;
        }
      } else if (item.hasName(referenceName)) {
        reference = new ItemInfo<>(item, i);
        if (target != null) {
          break;
        }
      }
    }

    if (target == null) {
      throw new UnknowLayerItem(targetName);
    }

    if (target.item().hasName(referenceName)) {
      reference = target;
    }

    if (reference == null) {
      throw new UnknowLayerItem(referenceName);
    }

    return new SearchResult(target, reference);
  }

  protected record SearchResult(ItemInfo<LayerItem> target, ItemInfo<LayerItem> reference) {
    public boolean isTargetInFrontOfReference() {
      return this.target.height() == this.reference.height() + 1;
    }

    public boolean isTargetBehindReference() {
      return this.target.height() - 1 == this.reference.height();
    }


    public boolean isTarget(int i) {
      return this.target.height() == i;
    }

    public boolean isReference(int i) {
      return this.reference.height() == i;
    }

    public boolean areTargetAndReferenceTheSame() {
      return this.target.height() == this.reference.height();
    }
  }

}
