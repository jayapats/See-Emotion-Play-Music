package com.spotify.faceapi.exception;

import com.spotify.faceapi.utility.AppConstants;

public class NoUserException extends RuntimeException {
  public NoUserException(String message) {
    if (message.equals(AppConstants.NOUSEREXCEPTION_MSG)) {
      throw new NoUserException(AppConstants.NOUSEREXCEPTION_MSG);
    }
  }
}
