package com.spotify.faceapi.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.faceapi.Exception.*;
import com.spotify.faceapi.Utility.AppConstants;
import com.spotify.faceapi.Utility.HttpHeaderUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class TopTrackofArtist {

    private final RestTemplate restTemplate;
    HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

    public Object getTopTracksofArtist(String token, String  artistID) throws NoAccountException, NoTrackException, UnAuthorizedException, ForbiddenException, BadRequestException, NotFoundException, NoArtistsException {
        HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);

        try{
        ResponseEntity<Object> response = restTemplate.exchange(AppConstants.TOP_TRACKS_OF_ARTIST_URL + artistID +"/top-tracks?market=ES" , HttpMethod.GET, entity,
                Object.class);

        System.out.print("Response Obj for getTopTracksofArtist: " + response.getBody());
        LinkedHashMap result_toptracksofArtist = (LinkedHashMap) response.getBody();


        if (result_toptracksofArtist.size() == 0) {
            throw new NoTrackException(AppConstants.NoTrackException_Msg);
        }

        ArrayList items_toptracksofArtist = (ArrayList) result_toptracksofArtist.get("tracks");
        return result_toptracksofArtist;

        } catch (HttpClientErrorException.Unauthorized e) {
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
