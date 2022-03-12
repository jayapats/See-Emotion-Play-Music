package com.spotify.faceapi.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class TrackAttribute {
    private final RestTemplate restTemplate;
    HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

    public Object getTrackAttributes(String token, String trackId) throws NoAccountException, NoAttributeException, UnAuthorizedException, ForbiddenException, BadRequestException, NotFoundException, NoArtistsException {
        HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);

        try{
        ResponseEntity<Object> response_att = restTemplate.exchange(AppConstants.TRACK_ATTRIBUTES_URL +trackId , HttpMethod.GET, entity,
                Object.class);
        System.out.print("Response Obj for att: " + response_att.getBody());


        LinkedHashMap result_att = (LinkedHashMap) response_att.getBody();

            if (result_att.size() == 0) {
                throw new NoAttributeException(AppConstants.NoAttributeException_Msg);
            }

        ArrayList<LinkedHashMap> items_att = (ArrayList) result_att.get("audio_features");

        return result_att;

        } catch (HttpClientErrorException.Unauthorized e) {
            throw new UnAuthorizedException(AppConstants.UnAuthorizedException_Msg);
        } catch (HttpClientErrorException.Forbidden e) {
            throw new ForbiddenException(AppConstants.ForbiddenException_Msg);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException(AppConstants.BadRequestException_Msg);
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException(AppConstants.NotFoundException_Msg);
        } catch (NoAttributeException e) {
            throw new NoArtistsException(AppConstants.NoAttributeException_Msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
