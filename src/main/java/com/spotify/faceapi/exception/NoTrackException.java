package com.spotify.faceapi.exception;

import com.spotify.faceapi.utility.AppConstants;

public class NoTrackException extends RuntimeException {
    public NoTrackException(String message) {
        if (message.equals(AppConstants.NO_TRACK_EXCEPTION_MSG)) {
            throw new RuntimeException(AppConstants.NO_TRACK_EXCEPTION_MSG);
        }
    }
}
