package com.spotify.faceapi.Service;

import com.spotify.faceapi.Entity.SaveTrackID;
import com.spotify.faceapi.Exception.*;
import com.spotify.faceapi.Utility.AppConstants;
import com.spotify.faceapi.Utility.HttpHeaderUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class RecentSavedTrackService {

    private final RestTemplate restTemplate;
    HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

    public Object getRecentSavedTracks(String token) throws NoAccountException, UnAuthorizedException, ForbiddenException, BadRequestException, NotFoundException, NoArtistsException {
        HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);

        String trackIDs = "7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B";

        try{
            ResponseEntity<Object> response = restTemplate.exchange(AppConstants.RECENT_SAVED_TRACKS_URL + trackIDs, HttpMethod.GET, entity,
                    Object.class);


            LinkedHashMap result = (LinkedHashMap) response.getBody();
            ArrayList items = (ArrayList) result.get("tracks");

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
