package com.spotify.faceapi.Controller;

import com.spotify.faceapi.Entity.SaveTrackID;
import com.spotify.faceapi.Entity.UserInfo;
import com.spotify.faceapi.Exception.*;
import com.spotify.faceapi.Utility.TermUtility;


import javax.servlet.http.HttpSession;

import com.spotify.faceapi.Service.*;
import com.spotify.faceapi.bean.SaveTrackList;
import com.spotify.faceapi.bean.Tracks_obj;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class SpotifyAPIController {

    private final ArtistService topArtistService;
    private final TopTrackService topTrackService;
    private final CustomizeMusicService customizeService;
    private final RecentlyPlayedTrackService recentPlayedTrackService;
    private final UserService userDetails;
    private final CurrentMusicService currentPlaying;
    private final SavedTrackService savedTrackService;
    SaveTrackList saveTrackList;

    @Autowired
    private SaveTrackService saveTrackService;

    @GetMapping(value = "/topArtists", produces = MediaType.TEXT_HTML_VALUE)
    public String topArtistsController(@RequestParam("term") final Integer term, final HttpSession session,
                                       final Model model) {
        System.out.println("term from UI: "+ term);
        try {
            model.addAttribute("artists",
                    topArtistService.getTopArtists((String) session.getAttribute("accessToken"), term));
            model.addAttribute("term", TermUtility.getTerm(term));
        } catch (NoTrackException exception) {
            return "dataUnavailable";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "topArtists";
    }

    @GetMapping(value = "/recentTracks", produces = MediaType.TEXT_HTML_VALUE)
    public String recentTracksController(final HttpSession session, final Model model) throws NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException, NoTrackException, ForbiddenException {
        model.addAttribute("tracks", recentPlayedTrackService.getHistory((String) session.getAttribute("accessToken")));
        return "recentTracks";
    }

    @GetMapping(value = "/topTracks", produces = MediaType.TEXT_HTML_VALUE)
    public String topTracksController(@RequestParam("term") final Integer term, final HttpSession session,
                                       final Model model) {
        try {
            model.addAttribute("tracks",
                    topTrackService.getTopTracks((String) session.getAttribute("accessToken"), term));
            model.addAttribute("term",  TermUtility.getTerm(term));
        } catch (NoAccountException exception) {
            return "dataUnavailable";
        } catch (ForbiddenException e) {
            e.printStackTrace();
        } catch (NoArtistsException e) {
            e.printStackTrace();
        } catch (BadRequestException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (UnAuthorizedException e) {
            e.printStackTrace();
        }
        return "topTracks";
    }

    @GetMapping(value = "/redirect", produces = MediaType.TEXT_HTML_VALUE)
    public String redirectSuccessController(final HttpSession session, final Model model) throws ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException {

        String token = (String) session.getAttribute("accessToken");
        model.addAttribute("accessToken", token);
        model.addAttribute("userName", userDetails.getUsername(token));

        try {
            model.addAttribute("currentPlaying", currentPlaying.getCurrentPlaying(token));
            model.addAttribute("display", 1);
        } catch (NoTrackException exception) {
            model.addAttribute("display", 0);
        } catch (NoArtistsException e) {
            e.printStackTrace();
        } catch (BadRequestException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (UnAuthorizedException e) {
            e.printStackTrace();
        } catch (ForbiddenException e) {
            e.printStackTrace();
        }
        return "main";
    }

    @GetMapping(value = "/savedTracks", produces = MediaType.TEXT_HTML_VALUE)
    public String savedTracksController(final HttpSession session, final Model model) throws NoTrackException, ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException {
        model.addAttribute("tracks", savedTrackService.getTracks((String) session.getAttribute("accessToken")));
        return "savedTracks";
    }

    @GetMapping(value = "/spotify", produces = MediaType.TEXT_HTML_VALUE)
    public String getCustomisedPlaylist(@RequestParam("emotion") String emotion,Model model, final HttpSession session) throws IOException, ParseException, SpotifyWebApiException {
//        SaveTrackList saveTrackList = new SaveTrackList();
        System.out.println("getCustomisedPlaylist Controller");
        System.out.println(emotion);
        try {
            model.addAttribute("tracks",
                    customizeService.getCustomTracks((String) session.getAttribute("accessToken"),emotion));
            model.addAttribute("term",  "Past 6 Months");
//            SavePlaylistService.savePlaylist((String) session.getAttribute("accessToken"),model);

        } catch (NoAccountException exception) {
            return "dataUnavailable";
        }
        System.out.println("Controller, SavetrackList: "+saveTrackList.getSaveTrackIdList());

        return "customized_tracks";

    }
//
//    @GetMapping(value = "/savePlaylist", produces = MediaType.TEXT_HTML_VALUE)
//    public String savePlaylistController(final HttpSession session, final Model model) throws NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException, NoTrackException, ForbiddenException {
//
//            System.out.println("Hi Saving");
//            return null;
//        }

    @GetMapping(value = "/savePlaylist", produces = MediaType.TEXT_HTML_VALUE)
    public String savePlaylistController(final HttpSession session, final Model model) throws Exception {
        System.out.println("Hi Saving");
        ArrayList<String> trackIDList = saveTrackList.getSaveTrackIdList();
        System.out.println("Controller, Save: "+ trackIDList);


        SaveTrackID response = new SaveTrackID();
        List<SaveTrackID> savetrackIDList = new ArrayList<>();


        for(String trackID:trackIDList) {
//            saveTrackID.setTrackID(trackID);
            SaveTrackID saveTrackID = new SaveTrackID();
            saveTrackID.setTrackID(trackID);
            savetrackIDList.add(saveTrackID);

        }

        saveTrackService.saveTrackID(savetrackIDList);

        return "main";
    }


}

