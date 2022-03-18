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

@Service
@RequiredArgsConstructor
public class TrackAttribute {
  private final RestTemplate restTemplate;
  HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

  public Object getTrackAttributes(String token, String trackId) throws Exception {
    HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);

    try {
      ResponseEntity<Object> responseAtt =
          restTemplate.exchange(
              AppConstants.TRACK_ATTRIBUTES_URL + trackId, HttpMethod.GET, entity, Object.class);

      LinkedHashMap<String, ArrayList<String>> resultAtt = (LinkedHashMap) responseAtt.getBody();

      if (resultAtt != null && responseAtt.getBody() != null) {
        return resultAtt;
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
