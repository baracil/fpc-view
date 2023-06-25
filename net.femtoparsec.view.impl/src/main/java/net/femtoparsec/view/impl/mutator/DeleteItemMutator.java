package net.femtoparsec.view.impl.mutator;

import lombok.RequiredArgsConstructor;
import net.femtoparsec.view.api.LayerItem;

@RequiredArgsConstructor
public class DeleteItemMutator extends LayerMutatorBase {

  private final String layerItemName;

  @Override
  public LayerItem[] mutate(LayerItem[] source) {
    final var position = this.getItemInfo(source, this.layerItemName).height();

    final var result = new LayerItem[source.length - 1];
    for (int i = 0, j = 0; i < source.length; i++) {
      if (i != position) {
        result[j++] = source[i];
      }
    }

    return result;
  }

}
