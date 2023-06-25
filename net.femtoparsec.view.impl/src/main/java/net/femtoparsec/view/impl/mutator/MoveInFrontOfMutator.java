package net.femtoparsec.view.impl.mutator;

import lombok.RequiredArgsConstructor;
import net.femtoparsec.view.api.LayerItem;

@RequiredArgsConstructor
public class MoveInFrontOfMutator extends LayerMutatorBase {

  private final String targetItemName;
  private final String referenceItemName;

  @Override
  public LayerItem[] mutate(LayerItem[] source) {
    final var searchResult = this.findItems(source, this.targetItemName, this.referenceItemName);

    if (searchResult.isTargetInFrontOfReference() || searchResult.areTargetAndReferenceTheSame()) {
      return source;
    }

    final var length = source.length;
    final var newItems = new LayerItem[length];
    var idx = 0;
    for (int i = 0; i < length; i++) {
      if (searchResult.isTarget(i)) {
        continue;
      }
      newItems[idx++] = source[i];
      if (searchResult.isReference(i)) {
        newItems[idx++] = searchResult.target().item();
      }
    }

    return newItems;
  }
}
