package com.spotify.faceapi.exception;

public class BadRequestException extends Exception {
  public BadRequestException(String message) {
    super(message);
  }
}
