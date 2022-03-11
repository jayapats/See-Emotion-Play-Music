package com.spotify.faceapi.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.faceapi.Exception.*;
import com.spotify.faceapi.Utility.AppConstants;
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

import java.io.FileNotFoundException;
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
    public void getTopTracksofArtist_Success() throws NoAccountException, ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException, NoTrackException, IOException, ParseException {

        JSONParser parser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();

        Object json_obj3  = parser.parse(new FileReader("C:\\PROJECT\\NEW\\faceapi\\SEPM\\src\\test\\java\\com\\spotify\\faceapi\\data\\TopArtiststracks.json"));
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
    public void getTopTracksofArtist_NoContent() throws NoAccountException, ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException {
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
        Assert.assertEquals(AppConstants.NoTrackException_Msg, Message);
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
        Assert.assertEquals(AppConstants.ForbiddenException_Msg, Message);
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
        Assert.assertEquals(AppConstants.BadRequestException_Msg, Message);
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
        Assert.assertEquals(AppConstants.NotFoundException_Msg, Message);
    }
}
