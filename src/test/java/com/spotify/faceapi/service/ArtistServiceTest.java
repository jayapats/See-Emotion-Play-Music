package com.spotify.faceapi.Service;

import com.spotify.faceapi.service.ArtistService;
import com.spotify.faceapi.service.TopTrackService;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

@RunWith(MockitoJUnitRunner.class)
public class ArtistServiceTest {
    @InjectMocks
    private ArtistService topArtistsService;

    @Mock
    private RestTemplate restTemplate;

    private Logger logger = Logger.getLogger(TopTrackService.class.getName());

    @Test
    public void getTopArtists_Success() throws Exception {
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

        LinkedHashMap res = (LinkedHashMap) topArtistsService.getTopArtists("1243",0);
        Assert.assertEquals(inputMap,res);

    }

    @Test
    public void getTopArtists_NoContent() throws Exception {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        ResponseEntity responseEntity = new ResponseEntity(map, HttpStatus.NO_CONTENT);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity);

        LinkedHashMap result = (LinkedHashMap) topArtistsService.getTopArtists("1243",0);
        Assert.assertEquals(map, result);
    }

    @Test
    public void getTopArtists_Forbidden() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenThrow(HttpClientErrorException.Forbidden.class);
        String Message = null;
        try{
            topArtistsService.getTopArtists("1243",0);
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.FORBIDDEN_EXCEPTION_MSG, Message);
    }

    @Test
    public void getTopArtists_BadRequest() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenThrow(HttpClientErrorException.BadRequest.class);
        String Message = null;
        try{
            topArtistsService.getTopArtists("1243",0);
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.BAD_REQUEST_EXCEPTION_MSG, Message);
    }

    @Test
    public void getTopArtists_InternalServerError() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>>any(),
                ArgumentMatchers.<Class<String>>any())).thenThrow(HttpClientErrorException.NotFound.class);
        String Message = null;
        try {
            topArtistsService.getTopArtists("1243", 0);
        } catch (Exception e) {
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.NOT_FOUND_EXCEPTION_MSG, Message);
    }

}
