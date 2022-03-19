package com.spotify.faceapi.controller;

import com.spotify.faceapi.exception.NoArtistsException;
import com.spotify.faceapi.exception.NoTrackException;
import com.spotify.faceapi.service.*;
import com.spotify.faceapi.utility.AppConstants;
import com.spotify.faceapi.utility.TermUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

@Controller
@AllArgsConstructor
public class SpotifyAPIController {

    private final ArtistService topArtistService;
    private final TopTrackService topTrackService;
    private final CustomizeMusicService customizeService;
    private final RecentlyPlayedTrackService recentPlayedTrackService;
    private final UserService userDetails;
    private final SavedTrackService savedTrackService;

    private static Logger logger = Logger.getLogger(SpotifyAPIController.class.getName());

    @GetMapping(value = "/topArtists", produces = MediaType.TEXT_HTML_VALUE)
    public String topArtistsController(
            @RequestParam("term") final Integer term, final HttpSession session, final Model model) {

        try {
            LinkedHashMap<String, ArrayList<String>> result =
                    (LinkedHashMap)
                            topArtistService.getTopArtists(
                                    (String) session.getAttribute(AppConstants.ACCESS_TOKEN), term);

            if (result == null) {
                model.addAttribute(AppConstants.ERROR_MSG, AppConstants.NO_ARTISTS_EXCEPTION_MSG);
                return AppConstants.NO_DATA_TEMPLATE;
            }

            model.addAttribute("artists", result);
            model.addAttribute("term", TermUtility.getTerm(term));

        } catch (Exception e) {
            model.addAttribute(AppConstants.ERROR_MSG, e);
            return AppConstants.NO_DATA_TEMPLATE;
        }
        return "topArtists";
    }

    @GetMapping(value = "/recentTracks", produces = MediaType.TEXT_HTML_VALUE)
    public String recentTracksController(final HttpSession session, final Model model)
            throws NoArtistsException, NoTrackException {
        try {
            LinkedHashMap<String, ArrayList<String>> result =
                    (LinkedHashMap)
                            recentPlayedTrackService.getHistory(
                                    (String) session.getAttribute(AppConstants.ACCESS_TOKEN));

            if (result == null) {
                model.addAttribute(AppConstants.ERROR_MSG, AppConstants.NO_TRACK_EXCEPTION_MSG);
                return AppConstants.NO_DATA_TEMPLATE;
            }
            model.addAttribute(AppConstants.TRACKS, result);
        } catch (Exception e) {
            model.addAttribute(AppConstants.ERROR_MSG, e);
            return AppConstants.NO_DATA_TEMPLATE;
        }
        return "recentTracks";
    }

    @GetMapping(value = "/topTracks", produces = MediaType.TEXT_HTML_VALUE)
    public String topTracksController(
            @RequestParam("term") final Integer term, final HttpSession session, final Model model) {
        try {
            LinkedHashMap<String, ArrayList<String>> result =
                    (LinkedHashMap)
                            topTrackService.getTopTracks(
                                    (String) session.getAttribute(AppConstants.ACCESS_TOKEN), term);

            if (result == null) {
                model.addAttribute(AppConstants.ERROR_MSG, AppConstants.NO_TRACK_EXCEPTION_MSG);
                return AppConstants.NO_DATA_TEMPLATE;
            }

            model.addAttribute(AppConstants.TRACKS, result);
            model.addAttribute("term", TermUtility.getTerm(term));
        } catch (Exception e) {
            model.addAttribute(AppConstants.ERROR_MSG, e);
            return AppConstants.NO_DATA_TEMPLATE;
        }
        return "topTracks";
    }

    @GetMapping(value = "/redirect", produces = MediaType.TEXT_HTML_VALUE)
    public String redirectSuccessController(final HttpSession session, final Model model)
            throws Exception {

        logger.info("Redirect Controller");
        String token = (String) session.getAttribute(AppConstants.ACCESS_TOKEN);
        model.addAttribute(AppConstants.ACCESS_TOKEN, token);
        model.addAttribute("userName", userDetails.getUsername(token));

        return "main";
    }

    @GetMapping(value = "/savedTracks", produces = MediaType.TEXT_HTML_VALUE)
    public String savedTracksController(final HttpSession session, final Model model) {
        try {
            LinkedHashMap<String, ArrayList<String>> result =
                    (LinkedHashMap)
                            savedTrackService.getTracks((String) session.getAttribute(AppConstants.ACCESS_TOKEN));

            if (result == null) {
                model.addAttribute(AppConstants.ERROR_MSG, AppConstants.NO_TRACK_EXCEPTION_MSG);
                return AppConstants.NO_DATA_TEMPLATE;
            }
            model.addAttribute(AppConstants.TRACKS, result);

        } catch (Exception e) {
            model.addAttribute(AppConstants.ERROR_MSG, e);
            return AppConstants.NO_DATA_TEMPLATE;
        }
        return "savedTracks";
    }

    @GetMapping(value = "/spotify", produces = MediaType.TEXT_HTML_VALUE)
    public String getCustomisedPlaylist(
            @RequestParam("emotion") String emotion, Model model, final HttpSession session) {

        logger.info("getCustomisedPlaylist Controller");
        logger.info(emotion);
        try {
            LinkedHashMap<String, ArrayList<String>> result =
                    (LinkedHashMap)
                            customizeService.getCustomTracks(
                                    (String) session.getAttribute(AppConstants.ACCESS_TOKEN), emotion);

            if (result == null) {
                model.addAttribute(AppConstants.ERROR_MSG, "No tracks matching the current mood of User!");
                return AppConstants.NO_DATA_TEMPLATE;
            }

            model.addAttribute(AppConstants.TRACKS, result);
            model.addAttribute("term", "Past 6 Months");

        } catch (Exception e) {
            model.addAttribute(AppConstants.ERROR_MSG, e);
            return AppConstants.NO_DATA_TEMPLATE;
        }
        return "customized_tracks";
    }
}
