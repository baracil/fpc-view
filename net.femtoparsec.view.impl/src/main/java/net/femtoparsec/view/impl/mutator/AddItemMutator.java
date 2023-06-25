package net.femtoparsec.view.impl.mutator;

import lombok.RequiredArgsConstructor;
import net.femtoparsec.view.api.DuplicateLayerItemName;
import net.femtoparsec.view.api.LayerItem;
import net.femtoparsec.view.impl.LayerMutator;

import java.util.Arrays;

@RequiredArgsConstructor
public class AddItemMutator implements LayerMutator {

  private final LayerItem layerItem;

  @Override
  public LayerItem[] mutate(LayerItem[] source) {
    final var sameNameExists = Arrays.stream(source).anyMatch(i -> i.hasName(this.layerItem.getName()));
    if (sameNameExists) {
      throw new DuplicateLayerItemName( this.layerItem.getName());
    }

    final var result = Arrays.copyOf(source, source.length + 1);
    result[source.length] = this.layerItem;
    return result;
  }
}
