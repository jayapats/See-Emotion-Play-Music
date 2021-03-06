package com.spotify.faceapi.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.spotify.faceapi.Exception.*;
import com.spotify.faceapi.Utility.AppConstants;
import com.spotify.faceapi.Utility.HttpHeaderUtility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SavedTrackService {

    private final RestTemplate restTemplate;
    HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

    public Object getTracks(String token) throws NoTrackException, UnAuthorizedException, ForbiddenException, BadRequestException, NotFoundException, NoArtistsException {
        HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);
        try {
        ResponseEntity<Object> response = restTemplate.exchange(AppConstants.SAVED_TRACKS_URL, HttpMethod.GET, entity, Object.class);
        LinkedHashMap result = (LinkedHashMap) response.getBody();

        ArrayList items = (ArrayList) result.get("items");

        if (items == null) {
                throw new NoArtistsException(AppConstants.NoArtistsException_Msg);
        }
        return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
