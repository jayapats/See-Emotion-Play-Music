package com.spotify.faceapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.faceapi.exception.*;
import com.spotify.faceapi.utility.AppConstants;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

@RunWith(MockitoJUnitRunner.class)
public class TopTrackofArtistTest {

    @InjectMocks
    private TopTrackofArtist topTrackofArtist;

    @Mock
    private RestTemplate restTemplate;

    private Logger logger = Logger.getLogger(TopTrackService.class.getName());

    @Test
    public void getTopTracksofArtist_Success() throws Exception {

        JSONParser parser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();

<<<<<<< HEAD:src/test/java/com/spotify/faceapi/Service/TopTrackofArtistTest.java
        Object json_obj3  = parser.parse(new FileReader("src/test/java/com/spotify/faceapi/data/TopArtiststracks.json"));
=======
        Object json_obj3  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopArtiststracks.json"));
>>>>>>> 8a9373b3161b7c051cf4d58c22a69a281ebbb574:src/test/java/com/spotify/faceapi/service/TopTrackofArtistTest.java
        Map<String, String> topArtiststracks_map1 = mapper.readValue(json_obj3.toString(), Map.class);

        ResponseEntity responseEntity = new ResponseEntity(topArtiststracks_map1, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity);

        LinkedHashMap res = (LinkedHashMap) topTrackofArtist.getTopTracksofArtist("AZT1010ZZ7H2","1mYsTxnqsietFxj1OgoGbG");
        Assert.assertEquals(topArtiststracks_map1,res);
    }

    @Test
    public void getTopTracksofArtist_NoContent() throws NoArtistsException {
        LinkedHashMap<String, ArrayList> inputMap = new LinkedHashMap<>();
        ResponseEntity responseEntity = new ResponseEntity(inputMap, HttpStatus.NO_CONTENT);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity);
        String Message = null;
        try{
            topTrackofArtist.getTopTracksofArtist("AZT1010ZZ7H2","1mYsTxnqsietFxj1OgoGbG");
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(null, Message);
    }

    @Test
    public void getTopTracksofArtist_Forbidden() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenThrow(HttpClientErrorException.Forbidden.class);
        String Message = null;
        try{
            topTrackofArtist.getTopTracksofArtist("AZT1010ZZ7H2","1mYsTxnqsietFxj1OgoGbG");
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.FORBIDDEN_EXCEPTION_MSG, Message);
    }

    @Test
    public void getTopTracksofArtist_BadRequest() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenThrow(HttpClientErrorException.BadRequest.class);
        String Message = null;
        try{
            topTrackofArtist.getTopTracksofArtist("AZT1010ZZ7H2","1mYsTxnqsietFxj1OgoGbG");
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.BAD_REQUEST_EXCEPTION_MSG, Message);
    }

    @Test
    public void getTopTracksofArtist_InternalServerError() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>>any(),
                ArgumentMatchers.<Class<String>>any())).thenThrow(HttpClientErrorException.NotFound.class);
        String Message = null;
        try {
            topTrackofArtist.getTopTracksofArtist("AZT1010ZZ7H2","1mYsTxnqsietFxj1OgoGbG");
        } catch (Exception e) {
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.NOT_FOUND_EXCEPTION_MSG, Message);
    }
}
