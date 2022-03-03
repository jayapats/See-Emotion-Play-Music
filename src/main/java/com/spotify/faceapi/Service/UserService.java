package com.spotify.faceapi.Service;

import com.spotify.faceapi.Exception.*;
import com.spotify.faceapi.Utility.AppConstants;
import com.spotify.faceapi.Utility.HttpHeaderUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

    public LinkedHashMap getUser(String token) throws UnAuthorizedException, ForbiddenException, BadRequestException, NotFoundException, NoArtistsException {
        HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);
        try{
        ResponseEntity<Object> response = restTemplate.exchange(AppConstants.USER_URL, HttpMethod.GET, entity, Object.class);
        LinkedHashMap result = (LinkedHashMap) response.getBody();

        if (result.size()==0) {
            throw new NoUserException(AppConstants.NoTrackException_Msg);
        }

        return result;
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getUsername(String token) throws ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException {
        LinkedHashMap user = getUser(token);
        return (String) user.get("display_name");
    }



}
