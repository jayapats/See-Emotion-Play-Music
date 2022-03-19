package com.spotify.faceapi.utility;

import java.security.SecureRandom;
import java.util.Base64;

public class EncoderUtility {
  public static String generate() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] codeVerifier = new byte[32];
    secureRandom.nextBytes(codeVerifier);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
  }
}