package com.spotify.faceapi.Service;

import com.spotify.faceapi.Exception.NoAccountException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spotify.faceapi.Exception.NoArtistsException;
import com.spotify.faceapi.Exception.NoAttributeException;
import com.spotify.faceapi.Exception.NoTrackException;
import com.spotify.faceapi.Utility.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomizeMusicService {

    private final ArtistService topArtistService;
    private final TopTrackService topTrackService;
    private final TrackAttribute trackAttribute;
    private final TopTrackofArtist topTrackofArtist;
    private final RestTemplate restTemplate;

    public Object getCustomTracks(String token, String emotion) throws NoAccountException, JsonProcessingException {

        try {
            //Getting top tracks
            LinkedHashMap result = (LinkedHashMap) topTrackService.getTopTracks(token, 1);
            ArrayList<LinkedHashMap> items = (ArrayList) result.get("items");

            if (items == null) {
                throw new NoTrackException(AppConstants.NoTrackException_Msg);
            }

            //Getting top artists
            LinkedHashMap result_topArtist = (LinkedHashMap) topArtistService.getTopFiveArtists(token, 1);
            ArrayList<LinkedHashMap> artist_items = (ArrayList) result_topArtist.get("items");

            if (artist_items == null) {
                throw new NoArtistsException(AppConstants.NoArtistsException_Msg);
            }

            for (Map artist_item : artist_items) {
                String artistId = artist_item.get("id").toString();
                System.out.print("Getting top tracks for artistId: " + artistId);

                //Getting Top tracks of Artists
                LinkedHashMap result_toptrackArtist = (LinkedHashMap) topTrackofArtist.getTopTracksofArtist(token, artistId);
                ArrayList<LinkedHashMap> toptrackArtist_items = (ArrayList) result_toptrackArtist.get("tracks");

                if (toptrackArtist_items == null) {
                    throw new NoTrackException("No Tracks Found for Artist: " + artistId);
                }

                for (Map artist_track : toptrackArtist_items) {
                    if (!items.contains(artist_track)) {
                        System.out.println("Adding new track: " + artist_track);
                        items.add((LinkedHashMap) artist_track);
                    } else {
                        System.out.println("Track data already exists. ");
                    }
                }

            }

            //Getting attributes of tracks
            ArrayList<LinkedHashMap> items_to_delete = new ArrayList<>();

            for (Map item : items) {
                String trackId = item.get("id").toString();
                System.out.println("getting attributes for track id:" + trackId);

                LinkedHashMap result_att = (LinkedHashMap) trackAttribute.getTrackAttributes(token, trackId);
                ArrayList<LinkedHashMap> items_att = (ArrayList) result_att.get("audio_features");

                if (items_att == null) {
                    throw new NoArtistsException("No Attributes Found for track: " + trackId);
                }

                for (Map item_att : items_att) {
                    double danceability = (double) item_att.get("danceability");
                    double valence = (double) item_att.get("valence");
                    double energy = (double) item_att.get("energy");
                    System.out.println("Audio features: ");
                    System.out.println("danceability: " + danceability + " valence: " + valence + " energy: " + energy);
                    if (emotion.equals("happy")) {
                        if (danceability < 0.6 || valence < 0.6 || energy < 0.6) {
                            items_to_delete.add((LinkedHashMap) item);
                            System.out.println("Filtering songs for mood: " + emotion);
                            System.out.println("items_to_delete loop: " + items_to_delete.toString());
                        }
                    } else if (emotion.equals("neutral")) {
                        if (danceability < 0.6 || valence < 0.6 || energy < 0.6) {
                            items_to_delete.add((LinkedHashMap) item);
                            System.out.println("Filtering songs for mood: " + emotion);
                            System.out.println("items_to_delete loop: " + items_to_delete.toString());
                        }
                    } else if (emotion.equals("sad")) {
                        if ((danceability >= 0.7 || danceability <= 0.4) || (valence >= 0.7 || valence <= 0.4)) {
                            items_to_delete.add((LinkedHashMap) item);
                            System.out.println("Filtering songs for mood: " + emotion);
                            System.out.println("items_to_delete loop: " + items_to_delete.toString());
                        }
                    } else if (emotion.equals("angry")) {
                        if ((danceability >= 0.6 || danceability <= 0.3) || (valence >= 0.6 || valence <= 0.3)) {
                            items_to_delete.add((LinkedHashMap) item);
                            System.out.println("Filtering songs for mood: " + emotion);
                            System.out.println("items_to_delete loop: " + items_to_delete.toString());
                        }
                    }
                }

            }

            if (items_to_delete.size() == 0) {
                System.out.println(" No items Filtered. ");
            }
            items.removeAll(items_to_delete);
            System.out.println(" After Removing " + result);

            if (items == null) {
                throw new NoTrackException("No tracks matching the current mood of User!");
            }

            return result;
        }
        catch (Exception exception) {
            System.out.println("Exception occured: "+exception);
            return "dataUnavailable";
        }
    }
}
