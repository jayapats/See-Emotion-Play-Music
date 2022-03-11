package com.spotify.faceapi.Service;

import com.spotify.faceapi.Exception.*;
import com.spotify.faceapi.Utility.AppConstants;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

@RunWith(MockitoJUnitRunner.class)
public class TopTrackServiceTest {

    @InjectMocks
    private TopTrackService topTrackService;

    @Mock
    private RestTemplate restTemplate;

    private Logger logger = Logger.getLogger(TopTrackService.class.getName());

    @Test
    public void getTopTracks_Success() throws NoAccountException, ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException {
        LinkedHashMap<String, ArrayList> inputMap = new LinkedHashMap<>();
        ArrayList arrayList= new ArrayList();
        arrayList.add("id1");
        arrayList.add("id2");
        inputMap.put("items",arrayList);
        ResponseEntity responseEntity = new ResponseEntity(inputMap, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity);

        LinkedHashMap res = (LinkedHashMap) topTrackService.getTopTracks("1243",0);
        Assert.assertEquals(inputMap,res);
    }

    @Test
    public void getTopTracks_NoContent() throws NoAccountException, ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException {
        LinkedHashMap<String, ArrayList> inputMap = new LinkedHashMap<>();
        ResponseEntity responseEntity = new ResponseEntity(inputMap, HttpStatus.NO_CONTENT);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity);
        String Message = null;
        try{
            topTrackService.getTopTracks("1243",0);
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.NoTrackException_Msg, Message);
    }

    @Test
    public void getTopTracks_Forbidden() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenThrow(HttpClientErrorException.Forbidden.class);
        String Message = null;
        try{
            topTrackService.getTopTracks("1243",0);
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.ForbiddenException_Msg, Message);
    }

    @Test
    public void getTopTracks_BadRequest() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenThrow(HttpClientErrorException.BadRequest.class);
        String Message = null;
        try{
            topTrackService.getTopTracks("1243",0);
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.BadRequestException_Msg, Message);
    }

    @Test
    public void getTopTracks_InternalServerError() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>>any(),
                ArgumentMatchers.<Class<String>>any())).thenThrow(HttpClientErrorException.NotFound.class);
        String Message = null;
        try {
            topTrackService.getTopTracks("1243",0);
        } catch (Exception e) {
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.NotFoundException_Msg, Message);
    }

}