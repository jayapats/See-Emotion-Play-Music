package com.spotify.faceapi.Controller;

import com.spotify.faceapi.Service.SpotifyService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class InitController {
    private final SpotifyService spotifyService;

    @GetMapping(value = "/main", produces = MediaType.TEXT_HTML_VALUE)
    public String showIndex(final Model model) {
        model.addAttribute("url", spotifyService.getAuthorizationURL());
        return "home";
    }

}
