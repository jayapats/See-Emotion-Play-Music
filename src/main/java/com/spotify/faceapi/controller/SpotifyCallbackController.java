package com.spotify.faceapi.controller;

import com.spotify.faceapi.service.AccessTokenService;
import com.spotify.faceapi.service.CurrentMusicService;
import com.spotify.faceapi.service.SpotifyService;
import com.spotify.faceapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
@AllArgsConstructor
public class SpotifyCallbackController {
  private final SpotifyService spotifyService;
  private final AccessTokenService accessToken;
  private final UserService userDetails;
  private final CurrentMusicService currentPlaying;
  private static Logger logger = Logger.getLogger(SpotifyCallbackController.class.getName());

  @GetMapping(value = "/callback", produces = MediaType.TEXT_HTML_VALUE)
  public String callbackController(
      @RequestParam(value = "code", required = false) final String code,
      @RequestParam(value = "error", required = false) final String error,
      final Model model,
      final HttpSession session)
      throws Exception {

    logger.info("Spotify Callback Controller");

    if (error != null) {
      model.addAttribute("url", spotifyService.getAuthorizationURL());
      return "failureConnect";
    }
    session.setAttribute("code", code);
    String token = accessToken.getToken(code);
    logger.info("access token: " + token);

    session.setAttribute("accessToken", token);
    model.addAttribute("accessToken", token);
    model.addAttribute("userName", userDetails.getUsername(token));

    return "main";
  }
}
