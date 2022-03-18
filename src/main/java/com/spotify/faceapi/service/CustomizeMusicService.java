package com.spotify.faceapi.service;

import com.spotify.faceapi.exception.NoArtistsException;
import com.spotify.faceapi.exception.NoAttributeException;
import com.spotify.faceapi.exception.NoTrackException;
import com.spotify.faceapi.utility.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CustomizeMusicService {

  private final ArtistService topArtistService;
  private final TopTrackService topTrackService;
  private final TrackAttribute trackAttribute;
  private final TopTrackofArtist topTrackofArtist;
  private final RestTemplate restTemplate;
  private static Logger logger = Logger.getLogger(CustomizeMusicService.class.getName());

  public Object getCustomTracks(String token, String emotion) throws Exception {

    try {
      // Getting top tracks
      LinkedHashMap result = (LinkedHashMap) topTrackService.getTopTracks(token, 1);
      ArrayList<LinkedHashMap> items = (ArrayList) result.get("items");

      // Getting top artists
      LinkedHashMap result_topArtist = (LinkedHashMap) topArtistService.getTopFiveArtists(token, 1);
      ArrayList<LinkedHashMap> artist_items = (ArrayList) result_topArtist.get("items");

      for (Map artist_item : artist_items) {
        String artistId = artist_item.get("id").toString();
        logger.info("Getting top tracks for artistId: " + artistId);

        // Getting Top tracks of Artists
        LinkedHashMap<String, ArrayList<String>> resultToptrackArtist =
            (LinkedHashMap) topTrackofArtist.getTopTracksofArtist(token, artistId);
        ArrayList<LinkedHashMap<String, ArrayList<String>>> toptrackArtistItems =
            (ArrayList) resultToptrackArtist.get("tracks");

        for (LinkedHashMap<String, ArrayList<String>> artist_track : toptrackArtistItems) {
          if (!items.contains(artist_track)) {
            logger.info("Adding new track: " + artist_track);
            items.add(artist_track);
          } else {
            logger.info("Track data already exists. ");
          }
        }
      }

      // Getting attributes of tracks
      ArrayList<LinkedHashMap> items_to_delete = new ArrayList<>();

      for (Map item : items) {
        String trackId = item.get("id").toString();
        System.out.println("getting attributes for track id:" + trackId);

        LinkedHashMap result_att =
            (LinkedHashMap) trackAttribute.getTrackAttributes(token, trackId);
        ArrayList<LinkedHashMap> items_att = (ArrayList) result_att.get("audio_features");

        if (items_att == null) {
          throw new NoArtistsException("No Attributes Found for track: " + trackId);
        }

        for (Map item_att : items_att) {
          double danceability = (double) item_att.get("danceability");
          double valence = (double) item_att.get("valence");
          double energy = (double) item_att.get("energy");
          System.out.println("Audio features: ");
          System.out.println(
              "danceability: " + danceability + " valence: " + valence + " energy: " + energy);
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
            if ((danceability >= 0.7 || danceability <= 0.4)
                || (valence >= 0.7 || valence <= 0.4)) {
              items_to_delete.add((LinkedHashMap) item);
              System.out.println("Filtering songs for mood: " + emotion);
              System.out.println("items_to_delete loop: " + items_to_delete.toString());
            }
          } else if (emotion.equals("angry")) {
            if ((danceability >= 0.6 || danceability <= 0.3)
                || (valence >= 0.6 || valence <= 0.3)) {
              items_to_delete.add((LinkedHashMap) item);
              System.out.println("Filtering songs for mood: " + emotion);
              System.out.println("items_to_delete loop: " + items_to_delete.toString());
            }
          }
        }
      }

      if (items_to_delete.isEmpty()) {
        System.out.println(" No items Filtered. ");
      }
      items.removeAll(items_to_delete);
      System.out.println(" After Removing " + result);

      if (items == null) {
        throw new NoTrackException("No tracks matching the current mood of User!");
      }

      return result;
    } catch (NoTrackException exception) {
      logger.info("No Tracks Found Exception!");
      return AppConstants.NO_DATA_TEMPLATE;
    } catch (NoArtistsException exception) {
      logger.info("No Artists Found Exception!");
      return AppConstants.NO_DATA_TEMPLATE;
    } catch (NoAttributeException exception) {
      logger.info("No Attributes Found Exception!");
      return "dataUnavailable";
    } catch (Exception exception) {
      logger.info("Exception occured: " + exception);
      return "dataUnavailable";
    }
  }
}
