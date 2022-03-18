package com.spotify.faceapi.utility;

public final class AppConstants {
  public static final String CLIENT_ID = "6f607cdf41e4421ba28cffe59ebeb12c";
  public static final String REDIRECT_URI = "http://localhost:8080/callback";
  public static final String TOP_ARTISTS_URL =
      "https://api.spotify.com/v1/me/top/artists?time_range=";
  public static final String TOP_ARTIST_URL_LIMIT =
      "https://api.spotify.com/v1/me/top/artists?limit=5&time_range=";
  public static final String CURRENT_PLAYING_URL =
      "https://api.spotify.com/v1/me/player/currently-playing";
  public static final String RECENT_PLAYED_TRACK_URL =
      "https://api.spotify.com/v1/me/player/recently-played?limit=50";
  public static final String TOP_TRACKS_OF_ARTIST_URL = "https://api.spotify.com/v1/artists/";
  public static final String TOP_TRACKS_URL =
      "https://api.spotify.com/v1/me/top/tracks?time_range=";
  public static final String TRACK_ATTRIBUTES_URL =
      "https://api.spotify.com/v1/audio-features?ids=";
  public static final String USER_URL = "https://api.spotify.com/v1/me";
  public static final String SAVED_TRACKS_URL = "https://api.spotify.com/v1/me/tracks?limit=50";
  public static final String TEST_DATA_PATH = "src/test/java/com/spotify/faceapi/data/";

  // String Literals
  public static final String ACCESS_TOKEN = "accessToken";
  public static final String ERROR_MSG = "error_msg";
  public static final String TRACKS = "tracks";

  // template name
  public static final String NO_DATA_TEMPLATE = "dataUnavailable";

  // Exception Messages
  public static final String UN_AUTHORIZED_EXCEPTION_MSG = "User not Authorized";
  public static final String FORBIDDEN_EXCEPTION_MSG =
      "User does not have permission to access this server";
  public static final String BAD_REQUEST_EXCEPTION_MSG = "Bad Request, try again";
  public static final String NOT_FOUND_EXCEPTION_MSG =
      "server encountered an unexpected condition that prevented it from fulfilling the request";
  public static final String NO_ARTISTS_EXCEPTION_MSG = "No Artists Found";
  public static final String NO_TRACK_EXCEPTION_MSG = "No Tracks Found";
  public static final String NO_ATTRIBUTE_EXCEPTION_MSG =
      "No Attributes were found for the current track";
  public static final String NOUSEREXCEPTION_MSG = "No User Found";

  private AppConstants() {}
}
