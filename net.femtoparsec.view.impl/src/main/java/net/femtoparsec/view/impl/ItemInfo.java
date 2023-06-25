package net.femtoparsec.view.impl;

import net.femtoparsec.view.api.LayerItem;

import java.util.Optional;

public record ItemInfo<T extends LayerItem>(T item, int height) {


  public <U extends LayerItem> Optional<ItemInfo<U>> cast(Class<U> type) {
    if (type.isInstance(this.item)) {
      return Optional.of(new ItemInfo<>(type.cast(this.item), this.height));
    }
    return Optional.empty();
  }
}
