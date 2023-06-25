package net.femtoparsec.view.impl.mutator;

import lombok.RequiredArgsConstructor;
import net.femtoparsec.view.api.LayerItem;

@RequiredArgsConstructor
public class MoveToBackMutator extends LayerMutatorBase {

  private final String layerItemName;

  @Override
  public LayerItem[] mutate(LayerItem[] source) {
    final var length = source.length;
    if (length == 0 || length == 1) {
      return source;
    }
    if (source[0].hasName(this.layerItemName)) {
      return source;
    }
    final var info = this.getItemInfo(source, this.layerItemName);
    final var index = info.height();

    final var result = new LayerItem[length];
    for (int i = 0, j = 1; i < length; i++) {
      if (i != index) {
        result[j++] = source[i];
      }
    }
    result[0] = source[index];
    return result;

  }

}
