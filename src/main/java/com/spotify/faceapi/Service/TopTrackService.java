package com.spotify.faceapi.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class TopTrackService {

    private final RestTemplate restTemplate;
    HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

    public Object getTopTracks(String token, int term) throws NoAccountException, UnAuthorizedException, ForbiddenException, BadRequestException, NotFoundException, NoArtistsException {
        HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);

        String terms[] = { "short_term", "medium_term", "long_term" };

        try{
        ResponseEntity<Object> response = restTemplate.exchange(AppConstants.TOP_TRACKS_URL + terms[term], HttpMethod.GET, entity,
                Object.class);


        LinkedHashMap result = (LinkedHashMap) response.getBody();
        ArrayList items = (ArrayList) result.get("items");

        if (items == null) {
            throw new NoTrackException(AppConstants.NoTrackException_Msg);
        }
        return result;
        } catch (
                HttpClientErrorException.Unauthorized e) {
            throw new UnAuthorizedException(AppConstants.UnAuthorizedException_Msg);
        } catch (HttpClientErrorException.Forbidden e) {
            throw new ForbiddenException(AppConstants.ForbiddenException_Msg);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException(AppConstants.BadRequestException_Msg);
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException(AppConstants.NotFoundException_Msg);
        } catch (NoTrackException e) {
            throw new NoArtistsException(AppConstants.NoTrackException_Msg);
        } catch (Exception e) {
        e.printStackTrace();
    }
        return null;
}

}
