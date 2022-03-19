package com.spotify.faceapi.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
public class CurrentMusicService {

    private final RestTemplate restTemplate;
    HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();
    LinkedHashMap<String, ArrayList<String>> result = new LinkedHashMap<>();

    public Map<String, ArrayList<String>> getCurrentPlaying(String token) throws Exception {
        HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);
        try {
            ResponseEntity<Object> response =
                    restTemplate.exchange(
                            AppConstants.CURRENT_PLAYING_URL, HttpMethod.GET, entity, Object.class);

            result = (LinkedHashMap) response.getBody();

            if (result != null && result.size() != 0) {
                return result;
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
        }
    }
}
