package com.spotify.faceapi.Service;

import java.util.LinkedHashMap;

import com.spotify.faceapi.Exception.*;
import com.spotify.faceapi.Utility.AppConstants;
import com.spotify.faceapi.Utility.HttpHeaderUtility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrentMusicService {

    private final RestTemplate restTemplate;
    HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

    public LinkedHashMap getCurrentPlaying(String token) throws NoTrackException, UnAuthorizedException, BadRequestException, NotFoundException, NoArtistsException, ForbiddenException {
        HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);
        try{
        ResponseEntity<Object> response = restTemplate.exchange(AppConstants.CURRENT_PLAYING_URL, HttpMethod.GET, entity, Object.class);
//        if (response.getStatusCodeValue() == 204) {
//            throw new RuntimeException("no track found");
//        }
        LinkedHashMap result = (LinkedHashMap) response.getBody();

        if (result.size()==0) {
                throw new NoTrackException(AppConstants.NoTrackException_Msg);
        }

        return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
