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

import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class UserService {

  private final RestTemplate restTemplate;
  HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

  public LinkedHashMap getUser(String token) throws ForbiddenException, BadRequestException, NotFoundException, NoArtistsException, UnauthorizedException, SpotifyException {
    HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);
    try{
      ResponseEntity<Object> response = restTemplate.exchange(AppConstants.USER_URL, HttpMethod.GET, entity, Object.class);
      LinkedHashMap result = (LinkedHashMap) response.getBody();

      if (result.size()==0) {
        throw new NoUserException(AppConstants.NO_TRACK_EXCEPTION_MSG);
      }

      return result;

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
    return null;
  }

  public String getUsername(String token) throws Exception {
    LinkedHashMap user = getUser(token);
    return (String) user.get("display_name");
  }
}
