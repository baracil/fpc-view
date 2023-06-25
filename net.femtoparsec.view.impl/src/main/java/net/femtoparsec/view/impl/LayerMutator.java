package net.femtoparsec.view.impl;

import net.femtoparsec.view.api.LayerItem;

public interface LayerMutator {

  LayerItem[] mutate(LayerItem[] source);
}
