package com.spotify.faceapi.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.faceapi.Exception.NoAccountException;
import com.spotify.faceapi.Exception.NoArtistsException;
import com.spotify.faceapi.Exception.NoAttributeException;
import com.spotify.faceapi.Exception.NoTrackException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.json.simple.parser.ParseException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomizeMusicServiceTest {

    @InjectMocks
    private  CustomizeMusicService customizeMusicService;

    @Mock
    private ArtistService topArtistService;

    @Mock
    private TopTrackService topTrackService;

    @Mock
    private TrackAttribute trackAttribute;

    @Mock
    private TopTrackofArtist topTrackofArtist;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void getCustomTracks_Sucess() throws Exception {

        JSONParser parser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();

        Object json_obj  = parser.parse(new FileReader("C:\\PROJECT\\NEW\\faceapi\\SEPM\\src\\test\\java\\com\\spotify\\faceapi\\data\\TopTracks.json"));
        Map<String, String> topTracks_map = mapper.readValue(json_obj.toString(), Map.class);
        Mockito.when(topTrackService.getTopTracks("AZT1010ZZ7H2",1)).thenReturn(topTracks_map);

        Object json_obj1  = parser.parse(new FileReader("C:\\PROJECT\\NEW\\faceapi\\SEPM\\src\\test\\java\\com\\spotify\\faceapi\\data\\TopArtists.json"));
        Map<String, String> topArtist_map = mapper.readValue(json_obj1.toString(), Map.class);
        Mockito.when(topArtistService.getTopArtists("AZT1010ZZ7H2",1)).thenReturn(topArtist_map);

        json_obj1  = parser.parse(new FileReader("C:\\PROJECT\\NEW\\faceapi\\SEPM\\src\\test\\java\\com\\spotify\\faceapi\\data\\TopFiveArtists.json"));
        Map<String, String> topFiveArtist_map = mapper.readValue(json_obj1.toString(), Map.class);
        Mockito.when(topArtistService.getTopFiveArtists("AZT1010ZZ7H2",1)).thenReturn(topFiveArtist_map);


        Object json_obj2  = parser.parse(new FileReader("C:\\PROJECT\\NEW\\faceapi\\SEPM\\src\\test\\java\\com\\spotify\\faceapi\\data\\TrackAttributes_tc1.json"));
        Map<String, String> trackAttribue_map1 = mapper.readValue(json_obj2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","75TjBQ1zUgpueA1fm8tCHP")).thenReturn(trackAttribue_map1);

        Object json_obj_at2  = parser.parse(new FileReader("C:\\PROJECT\\NEW\\faceapi\\SEPM\\src\\test\\java\\com\\spotify\\faceapi\\data\\TrackAttributes_tc2.json"));
        Map<String, String> trackAttribue_map2 = mapper.readValue(json_obj_at2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","3xQGfRh1FEqfTGUW3kkea2")).thenReturn(trackAttribue_map2);

        Object json_obj3  = parser.parse(new FileReader("C:\\PROJECT\\NEW\\faceapi\\SEPM\\src\\test\\java\\com\\spotify\\faceapi\\data\\TopArtiststracks.json"));
        Map<String, String> topArtiststracks_map1 = mapper.readValue(json_obj3.toString(), Map.class);
        Mockito.when(topTrackofArtist.getTopTracksofArtist("AZT1010ZZ7H2","1mYsTxnqsietFxj1OgoGbG")).thenReturn(topArtiststracks_map1);


        customizeMusicService.getCustomTracks("AZT1010ZZ7H2","happy");

    }

    @Test
    public void getCustomTracks_Nodata() throws Exception {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        Mockito.when(topTrackService.getTopTracks("AZT1010ZZ7H2",1)).thenReturn(map);
        Mockito.when(topArtistService.getTopArtists("AZT1010ZZ7H2",1)).thenReturn(map);
        Mockito.when(topArtistService.getTopFiveArtists("AZT1010ZZ7H2",1)).thenReturn(map);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","75TjBQ1zUgpueA1fm8tCHP")).thenReturn(map);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","3xQGfRh1FEqfTGUW3kkea2")).thenReturn(map);
        Mockito.when(topTrackofArtist.getTopTracksofArtist("AZT1010ZZ7H2","1mYsTxnqsietFxj1OgoGbG")).thenReturn(map);

        customizeMusicService.getCustomTracks("AZT1010ZZ7H2","happy");

    }


}
