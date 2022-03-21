package com.example.music.exception;

public class MusicServiceException extends CustomRuntimeException {

  private final String message;

  public MusicServiceException(String message) {
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
