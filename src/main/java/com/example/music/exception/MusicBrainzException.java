package com.example.music.exception;

public class MusicBrainzException extends CustomRuntimeException {

  private final String message;

  public MusicBrainzException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
