package com.spotify.faceapi.controller;

import com.spotify.faceapi.entity.UserInfo;
import com.spotify.faceapi.exception.SpotifyException;
import com.spotify.faceapi.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.spotify.faceapi.service.SpotifyService;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
@AllArgsConstructor
@Validated
public class MainController {

  private static Logger logger = Logger.getLogger(MainController.class.getName());

  @Autowired private AppUserService appuserService;

  private final SpotifyService spotifyService;

  @GetMapping("/")
  public String main(Model model) {

    return "login";
  }

  @GetMapping("/register_user")
  public String regUser(Model model) {

    return "register_user";
  }

  @PostMapping("/addUser")
  public String addUser(
      @RequestParam String username,
      @RequestParam String password,
      @RequestParam String spotifyUsername)
      throws SpotifyException {
    UserInfo userInfo = new UserInfo();
    UserInfo response;
    if (!username.isEmpty() && !password.isEmpty() && !spotifyUsername.isEmpty()) {
      userInfo.setUsername(username);
      userInfo.setPassword(password);
      userInfo.setSpotifyUsername(spotifyUsername);
      response = appuserService.saveUser(userInfo);
      if (response.getUsername().equals(username)) {
        return "login";
      }
    }
    return "register_user_failure";
  }

  @GetMapping(value = "/validateLogin", produces = MediaType.TEXT_HTML_VALUE)
  public String validateLogin(
      @RequestParam String username, @RequestParam String password, Model model) {
    logger.info("validateLogin!!!!");
    if (appuserService.validateUser(username, password)) {
      logger.info("spotifyService.getAuthorizationURL() :");
      logger.info(spotifyService.getAuthorizationURL());
      model.addAttribute("url", spotifyService.getAuthorizationURL());
      return "home";
    } else {
      return "login_failure";
    }
  }

  @GetMapping(value = "/faceapi", produces = MediaType.TEXT_HTML_VALUE)
  public String faceAPIHandler(Model model) {

    logger.info("faceAPIHandler called");

    return "faceapi";
  }

  @GetMapping(value = "/logout", produces = MediaType.TEXT_HTML_VALUE)
  public String logoutHandler(final HttpSession session) {
    session.invalidate();
    return "redirect:/?logout";
  }

  public String showIndex(final Model model) {
    model.addAttribute("url", spotifyService.getAuthorizationURL());
    return "home";
  }
}
