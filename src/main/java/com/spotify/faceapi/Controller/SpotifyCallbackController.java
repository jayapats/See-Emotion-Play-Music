package com.spotify.faceapi.Controller;

import com.spotify.faceapi.Exception.*;
import com.spotify.faceapi.Service.AccessTokenService;
import com.spotify.faceapi.Service.CurrentMusicService;
import com.spotify.faceapi.Service.SpotifyService;
import com.spotify.faceapi.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
@AllArgsConstructor
public class SpotifyCallbackController {
    private final SpotifyService spotifyService;
    private final AccessTokenService accessToken;
    private final UserService userDetails;
    private final CurrentMusicService currentPlaying;

    @GetMapping(value = "/callback", produces = MediaType.TEXT_HTML_VALUE)
    public String CallbackController(@RequestParam(value = "code", required = false) final String code,
                                     @RequestParam(value = "error", required = false) final String error, final Model model,
                                     final HttpSession session) throws ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException {

        System.out.println("Spotify Callback Controller");

        if (error != null) {
            model.addAttribute("url", spotifyService.getAuthorizationURL());
            return "failureConnect";
        }
        session.setAttribute("code", code);
        String token = accessToken.getToken(code);
        System.out.println("access token: " +token);

        session.setAttribute("accessToken", token);
        model.addAttribute("accessToken", token);
        model.addAttribute("userName", userDetails.getUsername(token));

        return "main";
    }

}
