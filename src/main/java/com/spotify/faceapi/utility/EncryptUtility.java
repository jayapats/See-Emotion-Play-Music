package com.spotify.faceapi.utility;

import lombok.extern.slf4j.Slf4j;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Base64;

@Slf4j
public class EncryptUtility {
  public static String generate(final String codeVerifier) {
    byte[] digest = null;
    try {
      byte[] bytes = codeVerifier.getBytes("US-ASCII");
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      messageDigest.update(bytes, 0, bytes.length);
      digest = messageDigest.digest();
    } catch (UnsupportedEncodingException | NoSuchAlgorithmException exception) {
      log.error("Unable to generate code challenge {}", exception);
    }
    return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
  }

  private EncryptUtility() {}
}
