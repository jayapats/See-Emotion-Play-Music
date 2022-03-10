package com.spotify.faceapi.Utility;

import com.spotify.faceapi.Exception.NoAttributeException;

public final class AppConstants {
    public static String CLIENT_ID = "6f607cdf41e4421ba28cffe59ebeb12c";
    public static String REDIRECT_URI = "http://localhost:8080/callback";
    public static String TopArtists_URL = "https://api.spotify.com/v1/me/top/artists?time_range=";
    public static String TopArtist_URL_limit = "https://api.spotify.com/v1/me/top/artists?limit=5&time_range=";
    public static String CURRENT_PLAYING_URL = "https://api.spotify.com/v1/me/player/currently-playing";
    public static String RECENT_PLAYED_TRACK_URL = "https://api.spotify.com/v1/me/player/recently-played?limit=50";
    public static String TOP_TRACKS_OF_ARTIST_URL = "https://api.spotify.com/v1/artists/";
    public static String TOP_TRACKS_URL = "https://api.spotify.com/v1/me/top/tracks?time_range=";
    public static String TRACK_ATTRIBUTES_URL = "https://api.spotify.com/v1/audio-features?ids=";
    public static String USER_URL = "https://api.spotify.com/v1/me";
    public static String SAVED_TRACKS_URL = "https://api.spotify.com/v1/me/tracks?limit=50";
    public static String RECENT_SAVED_TRACKS_URL = "https://api.spotify.com/v1/tracks?market=ES&ids=";


    //Exception Messages
    public static String UnAuthorizedException_Msg = "User not Authorized";
    public static String ForbiddenException_Msg = "User does not have permission to access this server";
    public static String BadRequestException_Msg = "Bad Request, try again";
    public static String NotFoundException_Msg = "Server encountered an unexpected condition that prevented it from fulfilling the request";
    public static String NoArtistsException_Msg = "No Artists Found";
    public static String NoTrackException_Msg = "No Tracks Found";
    public static String NoAttributeException_Msg = "No Attributes were found for the current track";
    public static String NoUserException_Msg = "No User Found";
    public AppConstants() {
    }
}
