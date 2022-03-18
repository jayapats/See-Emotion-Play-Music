package com.spotify.faceapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.faceapi.utility.AppConstants;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.util.ArrayList;
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

    JSONParser parser = new JSONParser();
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getCustomTracks_Sucess() throws Exception {
        Object json_obj  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopTracks.json"));
        Map<String, String> topTracks_map = mapper.readValue(json_obj.toString(), Map.class);
        Mockito.when(topTrackService.getTopTracks("AZT1010ZZ7H2",1)).thenReturn(topTracks_map);

        Object json_obj1  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopArtists.json"));
        Map<String, String> topArtist_map = mapper.readValue(json_obj1.toString(), Map.class);
        Mockito.when(topArtistService.getTopArtists("AZT1010ZZ7H2",1)).thenReturn(topArtist_map);

        json_obj1  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopFiveArtists.json"));
        Map<String, String> topFiveArtist_map = mapper.readValue(json_obj1.toString(), Map.class);
        Mockito.when(topArtistService.getTopFiveArtists("AZT1010ZZ7H2",1)).thenReturn(topFiveArtist_map);


        Object json_obj2  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_tc1.json"));
        Map<String, String> trackAttribue_map1 = mapper.readValue(json_obj2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","75TjBQ1zUgpueA1fm8tCHP")).thenReturn(trackAttribue_map1);

        Object json_obj_at2  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_tc2.json"));
        Map<String, String> trackAttribue_map2 = mapper.readValue(json_obj_at2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","3xQGfRh1FEqfTGUW3kkea2")).thenReturn(trackAttribue_map2);

        Object json_obj3  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopArtiststracks.json"));
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

    @Test
    public void getCustomTracks_Happy() throws Exception {

        Object json_obj  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopTracks.json"));
        Map<String, String> topTracks_map = mapper.readValue(json_obj.toString(), Map.class);
        Mockito.when(topTrackService.getTopTracks("AZT1010ZZ7H2",1)).thenReturn(topTracks_map);

        Object json_obj1  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopFiveArtists.json"));
        Map<String, String> topFiveArtist_map = mapper.readValue(json_obj1.toString(), Map.class);
        Mockito.when(topArtistService.getTopFiveArtists("AZT1010ZZ7H2",1)).thenReturn(topFiveArtist_map);

        Object json_obj3  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopArtiststracks2.json"));
        Map<String, String> topArtiststracks_map1 = mapper.readValue(json_obj3.toString(), Map.class);
        Mockito.when(topTrackofArtist.getTopTracksofArtist("AZT1010ZZ7H2","1mYsTxnqsietFxj1OgoGbG")).thenReturn(topArtiststracks_map1);

        Object json_obj2  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_angry.json"));
        Map<String, String> trackAttribue_map1 = mapper.readValue(json_obj2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","75TjBQ1zUgpueA1fm8tCHP")).thenReturn(trackAttribue_map1);

        Object json_obj_at2  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_happy.json"));
        Map<String, String> trackAttribue_map2 = mapper.readValue(json_obj_at2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","3xQGfRh1FEqfTGUW3kkea2")).thenReturn(trackAttribue_map2);

        Object json_obj_at3  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_neutral.json"));
        Map<String, String> trackAttribue_map3 = mapper.readValue(json_obj_at3.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","75TjBQ1zUgpueA1fm8tCHP1")).thenReturn(trackAttribue_map3);

        Object json_obj_at4  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_sad.json"));
        Map<String, String> trackAttribue_map4 = mapper.readValue(json_obj_at4.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","3xQGfRh1FEqfTGUW3kkea21")).thenReturn(trackAttribue_map4);

        LinkedHashMap result = (LinkedHashMap) customizeMusicService.getCustomTracks("AZT1010ZZ7H2","happy");
        ArrayList<LinkedHashMap> items = (ArrayList) result.get("items");
        Assert.assertEquals("3xQGfRh1FEqfTGUW3kkea2",items.get(0).get("id"));

    }

    @Test
    public void getCustomTracks_Sad() throws Exception {

        Object json_obj  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopTracks.json"));
        Map<String, String> topTracks_map = mapper.readValue(json_obj.toString(), Map.class);
        Mockito.when(topTrackService.getTopTracks("AZT1010ZZ7H2",1)).thenReturn(topTracks_map);

        Object json_obj1  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopFiveArtists.json"));
        Map<String, String> topFiveArtist_map = mapper.readValue(json_obj1.toString(), Map.class);
        Mockito.when(topArtistService.getTopFiveArtists("AZT1010ZZ7H2",1)).thenReturn(topFiveArtist_map);

        Object json_obj3  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopArtiststracks2.json"));
        Map<String, String> topArtiststracks_map1 = mapper.readValue(json_obj3.toString(), Map.class);
        Mockito.when(topTrackofArtist.getTopTracksofArtist("AZT1010ZZ7H2","1mYsTxnqsietFxj1OgoGbG")).thenReturn(topArtiststracks_map1);

        Object json_obj2  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_angry.json"));
        Map<String, String> trackAttribue_map1 = mapper.readValue(json_obj2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","75TjBQ1zUgpueA1fm8tCHP")).thenReturn(trackAttribue_map1);

        Object json_obj_at2  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_happy.json"));
        Map<String, String> trackAttribue_map2 = mapper.readValue(json_obj_at2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","3xQGfRh1FEqfTGUW3kkea2")).thenReturn(trackAttribue_map2);

        Object json_obj_at3  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_neutral.json"));
        Map<String, String> trackAttribue_map3 = mapper.readValue(json_obj_at3.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","75TjBQ1zUgpueA1fm8tCHP1")).thenReturn(trackAttribue_map3);

        Object json_obj_at4  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_sad.json"));
        Map<String, String> trackAttribue_map4 = mapper.readValue(json_obj_at4.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","3xQGfRh1FEqfTGUW3kkea21")).thenReturn(trackAttribue_map4);

        LinkedHashMap result = (LinkedHashMap) customizeMusicService.getCustomTracks("AZT1010ZZ7H2","sad");
        ArrayList<LinkedHashMap> items = (ArrayList) result.get("items");
        Assert.assertEquals("3xQGfRh1FEqfTGUW3kkea21",items.get(0).get("id"));
    }

    @Test
    public void getCustomTracks_Neutral() throws Exception {

        Object json_obj  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopTracks.json"));
        Map<String, String> topTracks_map = mapper.readValue(json_obj.toString(), Map.class);
        Mockito.when(topTrackService.getTopTracks("AZT1010ZZ7H2",1)).thenReturn(topTracks_map);

        Object json_obj1  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopFiveArtists.json"));
        Map<String, String> topFiveArtist_map = mapper.readValue(json_obj1.toString(), Map.class);
        Mockito.when(topArtistService.getTopFiveArtists("AZT1010ZZ7H2",1)).thenReturn(topFiveArtist_map);

        Object json_obj3  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopArtiststracks2.json"));
        Map<String, String> topArtiststracks_map1 = mapper.readValue(json_obj3.toString(), Map.class);
        Mockito.when(topTrackofArtist.getTopTracksofArtist("AZT1010ZZ7H2","1mYsTxnqsietFxj1OgoGbG")).thenReturn(topArtiststracks_map1);

        Object json_obj2  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_angry.json"));
        Map<String, String> trackAttribue_map1 = mapper.readValue(json_obj2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","75TjBQ1zUgpueA1fm8tCHP")).thenReturn(trackAttribue_map1);

        Object json_obj_at2  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_happy.json"));
        Map<String, String> trackAttribue_map2 = mapper.readValue(json_obj_at2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","3xQGfRh1FEqfTGUW3kkea2")).thenReturn(trackAttribue_map2);

        Object json_obj_at3  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_neutral.json"));
        Map<String, String> trackAttribue_map3 = mapper.readValue(json_obj_at3.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","75TjBQ1zUgpueA1fm8tCHP1")).thenReturn(trackAttribue_map3);

        Object json_obj_at4  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_sad.json"));
        Map<String, String> trackAttribue_map4 = mapper.readValue(json_obj_at4.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","3xQGfRh1FEqfTGUW3kkea21")).thenReturn(trackAttribue_map4);

        LinkedHashMap result = (LinkedHashMap) customizeMusicService.getCustomTracks("AZT1010ZZ7H2","neutral");
        ArrayList<LinkedHashMap> items = (ArrayList) result.get("items");

        for (Map item : items) {
            if(item.get("id").equals("75TjBQ1zUgpueA1fm8tCHP1") || item.get("id").equals("3xQGfRh1FEqfTGUW3kkea2"))
            {
                Assert.assertTrue(true);
            }
            else{
                Assert.assertTrue(false);
            }
        }


    }

    @Test
    public void getCustomTracks_Angry() throws Exception {

        Object json_obj  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopTracks.json"));
        Map<String, String> topTracks_map = mapper.readValue(json_obj.toString(), Map.class);
        Mockito.when(topTrackService.getTopTracks("AZT1010ZZ7H2",1)).thenReturn(topTracks_map);

        Object json_obj1  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopFiveArtists.json"));
        Map<String, String> topFiveArtist_map = mapper.readValue(json_obj1.toString(), Map.class);
        Mockito.when(topArtistService.getTopFiveArtists("AZT1010ZZ7H2",1)).thenReturn(topFiveArtist_map);

        Object json_obj3  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TopArtiststracks2.json"));
        Map<String, String> topArtiststracks_map1 = mapper.readValue(json_obj3.toString(), Map.class);
        Mockito.when(topTrackofArtist.getTopTracksofArtist("AZT1010ZZ7H2","1mYsTxnqsietFxj1OgoGbG")).thenReturn(topArtiststracks_map1);

        Object json_obj2  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_angry.json"));
        Map<String, String> trackAttribue_map1 = mapper.readValue(json_obj2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","75TjBQ1zUgpueA1fm8tCHP")).thenReturn(trackAttribue_map1);

        Object json_obj_at2  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_happy.json"));
        Map<String, String> trackAttribue_map2 = mapper.readValue(json_obj_at2.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","3xQGfRh1FEqfTGUW3kkea2")).thenReturn(trackAttribue_map2);

        Object json_obj_at3  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_neutral.json"));
        Map<String, String> trackAttribue_map3 = mapper.readValue(json_obj_at3.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","75TjBQ1zUgpueA1fm8tCHP1")).thenReturn(trackAttribue_map3);

        Object json_obj_at4  = parser.parse(new FileReader(AppConstants.TEST_DATA_PATH +"TrackAttributes_sad.json"));
        Map<String, String> trackAttribue_map4 = mapper.readValue(json_obj_at4.toString(), Map.class);
        Mockito.when(trackAttribute.getTrackAttributes("AZT1010ZZ7H2","3xQGfRh1FEqfTGUW3kkea21")).thenReturn(trackAttribue_map4);

        LinkedHashMap result = (LinkedHashMap) customizeMusicService.getCustomTracks("AZT1010ZZ7H2","angry");
        ArrayList<LinkedHashMap> items = (ArrayList) result.get("items");
        Assert.assertEquals("75TjBQ1zUgpueA1fm8tCHP",items.get(0).get("id"));
    }


}
