package com.spotify.faceapi.Service;

import com.spotify.faceapi.exception.*;
import com.spotify.faceapi.service.CurrentMusicService;
import com.spotify.faceapi.utility.AppConstants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.exceptions.detailed.ForbiddenException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;

import java.util.LinkedHashMap;

@RunWith(MockitoJUnitRunner.class)
public class CurrentMusicServiceTest {

    @InjectMocks
    private CurrentMusicService currentMusicService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testCurrentPlaying_NoContent() throws NoTrackException, NoArtistsException {
        LinkedHashMap<String, String> inputMap= new LinkedHashMap<>();
        ResponseEntity response = new ResponseEntity(inputMap,HttpStatus.NO_CONTENT);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenReturn(response);

        String Message = null;
        try{
            currentMusicService.getCurrentPlaying("XWL00XCVS00099");
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.NO_TRACK_EXCEPTION_MSG, Message);
    }

    @Test
    public void testCurrentPlaying_Success() throws Exception {
        LinkedHashMap<String, String> inputMap= new LinkedHashMap<>();
        inputMap.put("token","AZYUUIU0ZZUY");
        ResponseEntity response = new ResponseEntity(inputMap,HttpStatus.ACCEPTED);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenReturn(response);

        LinkedHashMap res = (LinkedHashMap) currentMusicService.getCurrentPlaying("XWL00XCVS00099");
        Assert.assertEquals(inputMap,res);

         }

    @Test
    public void getTopTracks_Forbidden() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenThrow(HttpClientErrorException.Forbidden.class);
        String Message = null;
        try{
            currentMusicService.getCurrentPlaying("XWL00XCVS00099");
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.FORBIDDEN_EXCEPTION_MSG, Message);
    }

    @Test
    public void getTopTracks_BadRequest() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenThrow(HttpClientErrorException.BadRequest.class);
        String Message = null;
        try{
            currentMusicService.getCurrentPlaying("XWL00XCVS00099");
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.BAD_REQUEST_EXCEPTION_MSG, Message);
    }

    @Test
    public void getTopTracks_InternalServerError() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>>any(),
                ArgumentMatchers.<Class<String>>any())).thenThrow(HttpClientErrorException.NotFound.class);
        String Message = null;
        try {
            currentMusicService.getCurrentPlaying("XWL00XCVS00099");
        } catch (Exception e) {
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.NOT_FOUND_EXCEPTION_MSG, Message);
    }
}
