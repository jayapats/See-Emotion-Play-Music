package com.spotify.faceapi.exception;

import com.spotify.faceapi.utility.AppConstants;

public class NoArtistsException extends RuntimeException {
    public NoArtistsException(String message) {
        if (message.equals(AppConstants.NO_ARTISTS_EXCEPTION_MSG)) {
            throw new NoArtistsException(AppConstants.NO_ARTISTS_EXCEPTION_MSG);
        }
    }
}
