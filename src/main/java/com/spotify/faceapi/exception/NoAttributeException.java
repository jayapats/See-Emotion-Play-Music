package com.spotify.faceapi.exception;

import com.spotify.faceapi.utility.AppConstants;

public class NoAttributeException extends RuntimeException {
  public NoAttributeException(String message) {
    if (message.equals(AppConstants.NO_ATTRIBUTE_EXCEPTION_MSG)) {
      throw new NoAttributeException(AppConstants.NO_ATTRIBUTE_EXCEPTION_MSG);
    }
  }
}
