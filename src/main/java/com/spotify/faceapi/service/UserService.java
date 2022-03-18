package com.spotify.faceapi.service;

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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

  private final RestTemplate restTemplate;
  HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

  public Map<String, ArrayList<String>> getUser(String token) throws Exception {
    HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);
    LinkedHashMap<String, ArrayList<String>> result = new LinkedHashMap<>();
    try {
      ResponseEntity<Object> response =
          restTemplate.exchange(AppConstants.USER_URL, HttpMethod.GET, entity, Object.class);
      result = (LinkedHashMap) response.getBody();

      if (result != null && result.size() != 0) {
        return result;
      }
    } catch (HttpClientErrorException.Unauthorized e) {
      throw new UnauthorizedException(AppConstants.UN_AUTHORIZED_EXCEPTION_MSG);
    } catch (HttpClientErrorException.Forbidden e) {
      throw new ForbiddenException(AppConstants.FORBIDDEN_EXCEPTION_MSG);
    } catch (HttpClientErrorException.BadRequest e) {
      throw new BadRequestException(AppConstants.BAD_REQUEST_EXCEPTION_MSG);
    } catch (HttpClientErrorException.NotFound e) {
      throw new SpotifyException(AppConstants.NOT_FOUND_EXCEPTION_MSG);
    } catch (NoUserException e) {
      throw new NoUserException(AppConstants.NOUSEREXCEPTION_MSG);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public String getUsername(String token) throws Exception {
    Map<String, ArrayList<String>> user = getUser(token);
    return String.valueOf(user.get("display_name"));
  }
}
