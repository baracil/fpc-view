package net.femtoparsec.view.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.femtoparsec.view.api.LayerItem.Layouter;
import net.femtoparsec.view.api.Layout;

@RequiredArgsConstructor
public class BorderLayouter implements Layouter {

  @Getter
  private final String name;

  private final double top;
  private final double left;
  private final double bottom;
  private final double right;

  public BorderLayouter(String name, double width) {
    this(name, width, width, width, width);
  }

  @Override
  public Layout layout(Layout layout) {
    return layout.applyBorder(this.top, this.right, this.bottom, this.left);
  }
}
