package com.example.music.exception;

public class WikiDataException extends CustomRuntimeException {

  private final String message;

  public WikiDataException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public int getResponseCode() {
    return 400;
  }
}
