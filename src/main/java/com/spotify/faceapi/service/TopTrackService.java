package com.spotify.faceapi.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.spotify.faceapi.exception.*;
import com.spotify.faceapi.utility.AppConstants;
import com.spotify.faceapi.utility.HttpHeaderUtility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import se.michaelthelin.spotify.exceptions.detailed.ForbiddenException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;

@Service
@RequiredArgsConstructor
public class TopTrackService {

  private final RestTemplate restTemplate;
  HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

  public Object getTopTracks(String token, int term) throws Exception {
    HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);

    String[] terms = {"short_term", "medium_term", "long_term"};

    try {
      ResponseEntity<Object> response =
          restTemplate.exchange(
              AppConstants.TOP_TRACKS_URL + terms[term], HttpMethod.GET, entity, Object.class);

      LinkedHashMap<String, ArrayList<String>> result = (LinkedHashMap) response.getBody();

      if (result != null) {
        return result;
      }

    } catch (HttpClientErrorException.Unauthorized e) {
      throw new UnauthorizedException(AppConstants.UN_AUTHORIZED_EXCEPTION_MSG);
    } catch (HttpClientErrorException.Forbidden e) {
      throw new ForbiddenException(AppConstants.FORBIDDEN_EXCEPTION_MSG);
    } catch (HttpClientErrorException.BadRequest e) {
      throw new BadRequestException(AppConstants.BAD_REQUEST_EXCEPTION_MSG);
    } catch (HttpClientErrorException.NotFound e) {
      throw new Exception(AppConstants.NOT_FOUND_EXCEPTION_MSG);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}