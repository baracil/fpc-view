package net.femtoparsec.view.api;

public class ViewException extends RuntimeException {
  public ViewException() {
  }

  public ViewException(String message) {
    super(message);
  }

  public ViewException(String message, Throwable cause) {
    super(message, cause);
  }

  public ViewException(Throwable cause) {
    super(cause);
  }

}
