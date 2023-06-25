package net.femtoparsec.view.api;

import java.awt.*;

public record Layout(Graphics2D graphics2D, double width, double height) {

  public Layout applyBorder(double top, double right, double bottom, double left) {
    this.graphics2D.translate(top, left);
    return new Layout(this.graphics2D, this.width - left - right, this.height - top - bottom);
  }

}
