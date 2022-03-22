package com.example.music.exception;

public class CoverArtArchiveException extends CustomRuntimeException {

  private final String message;

  public CoverArtArchiveException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
