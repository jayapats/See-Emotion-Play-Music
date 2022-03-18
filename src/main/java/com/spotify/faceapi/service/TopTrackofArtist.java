package com.spotify.faceapi.service;

import com.spotify.faceapi.exception.*;
import com.spotify.faceapi.utility.AppConstants;
import com.spotify.faceapi.utility.HttpHeaderUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.exceptions.detailed.ForbiddenException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class TopTrackofArtist {

  private final RestTemplate restTemplate;
  HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();
  private static Logger logger = Logger.getLogger(TopTrackofArtist.class.getName());

  public Object getTopTracksofArtist(String token, String artistID) throws Exception {
    HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);

    try {
      ResponseEntity<Object> response =
          restTemplate.exchange(
              AppConstants.TOP_TRACKS_OF_ARTIST_URL + artistID + "/top-tracks?market=ES",
              HttpMethod.GET,
              entity,
              Object.class);

      logger.info("Response Obj for getTopTracksofArtist: " + response.getBody());
      LinkedHashMap<String, ArrayList<String>> resultToptracksofArtist =
          (LinkedHashMap) response.getBody();

      if (resultToptracksofArtist != null && resultToptracksofArtist.size() != 0) {
        return resultToptracksofArtist;
      } else {
        throw new NoTrackException(AppConstants.NO_TRACK_EXCEPTION_MSG);
      }

    } catch (HttpClientErrorException.Unauthorized e) {
      throw new UnauthorizedException(AppConstants.UN_AUTHORIZED_EXCEPTION_MSG);
    } catch (HttpClientErrorException.Forbidden e) {
      throw new ForbiddenException(AppConstants.FORBIDDEN_EXCEPTION_MSG);
    } catch (HttpClientErrorException.BadRequest e) {
      throw new BadRequestException(AppConstants.BAD_REQUEST_EXCEPTION_MSG);
    } catch (HttpClientErrorException.NotFound e) {
      throw new Exception(AppConstants.NOT_FOUND_EXCEPTION_MSG);
    } catch (NoTrackException e) {
      throw new NoArtistsException(AppConstants.NO_TRACK_EXCEPTION_MSG);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
