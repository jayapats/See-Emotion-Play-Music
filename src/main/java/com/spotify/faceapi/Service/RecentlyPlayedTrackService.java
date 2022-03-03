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
public class RecentlyPlayedTrackService {
    private final RestTemplate restTemplate;
    HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

    public Object getHistory(String token) throws UnAuthorizedException, BadRequestException, NotFoundException, NoArtistsException, NoTrackException, ForbiddenException {
        HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);
        try{
        ResponseEntity<Object> response = restTemplate.exchange(AppConstants.RECENT_PLAYED_TRACK_URL, HttpMethod.GET, entity, Object.class);
        LinkedHashMap result = (LinkedHashMap) response.getBody();
        if (result == null) {
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
            throw new NoTrackException(AppConstants.NoTrackException_Msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
