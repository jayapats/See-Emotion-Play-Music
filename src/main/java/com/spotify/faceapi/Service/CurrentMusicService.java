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

        LinkedHashMap result = (LinkedHashMap) response.getBody();

        if (result.size()==0) {
                throw new NoTrackException(AppConstants.NoTrackException_Msg);
        }

        return result;
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
