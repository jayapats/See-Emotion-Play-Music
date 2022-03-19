package com.spotify.faceapi.exception;

import com.spotify.faceapi.utility.AppConstants;

public class NoTrackException extends Exception {
    public NoTrackException(String message) throws Exception {
        if (message.equals(AppConstants.NO_TRACK_EXCEPTION_MSG)) {
            throw new Exception(AppConstants.NO_TRACK_EXCEPTION_MSG);
        }
    }
}
