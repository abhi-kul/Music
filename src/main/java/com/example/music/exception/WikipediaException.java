package com.example.music.exception;

public class WikipediaException extends CustomRuntimeException {
  private final String message;

  public WikipediaException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
