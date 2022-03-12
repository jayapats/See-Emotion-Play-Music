package com.spotify.faceapi.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ArtistService {
    private final RestTemplate restTemplate;
    HttpHeaderUtility httpHeaderUtility = new HttpHeaderUtility();

    public Object getTopArtists(String token, int term) throws Exception {
        HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);

        String terms[] = {"short_term", "medium_term", "long_term"};
        try {
            ResponseEntity<Object> response = restTemplate.exchange(AppConstants.TopArtists_URL + terms[term], HttpMethod.GET, entity,
                    Object.class);
            System.out.print("Response Obj for getTopArtists: " + response.getBody());

            LinkedHashMap result = (LinkedHashMap) response.getBody();
            ArrayList items = (ArrayList) result.get("items");

        if (items == null) {
                throw new NoArtistsException(AppConstants.NoArtistsException_Msg);
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
        } catch (NoArtistsException e) {
            throw new NoArtistsException(AppConstants.NoArtistsException_Msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getTopFiveArtists(String token, int term) throws NoArtistsException, UnAuthorizedException, BadRequestException, NotFoundException {
        HttpEntity<String> entity = httpHeaderUtility.setHeaders(token);

        String terms[] = {"short_term", "medium_term", "long_term"};

        try {
            ResponseEntity<Object> response = restTemplate.exchange(AppConstants.TopArtist_URL_limit + terms[term], HttpMethod.GET, entity,
                    Object.class);

            System.out.print("Response Obj for getTopArtists: " + response.getBody());
            LinkedHashMap result = (LinkedHashMap) response.getBody();

            ArrayList items = (ArrayList) result.get("items");

            if (items == null) {
                throw new NoArtistsException(AppConstants.NoArtistsException_Msg);
            }
            return result;

        } catch (HttpClientErrorException.Unauthorized e) {
            throw new UnAuthorizedException(AppConstants.UnAuthorizedException_Msg);
        } catch (HttpClientErrorException.Forbidden e) {
            throw new UnAuthorizedException(AppConstants.UnAuthorizedException_Msg);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException(AppConstants.BadRequestException_Msg);
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException(AppConstants.NotFoundException_Msg);
        } catch (NoArtistsException e) {
            throw new NoArtistsException(AppConstants.NoArtistsException_Msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}