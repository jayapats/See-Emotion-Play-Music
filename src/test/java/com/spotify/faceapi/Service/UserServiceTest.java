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

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private RestTemplate restTemplate;

    private Logger logger = Logger.getLogger(UserServiceTest.class.getName());

    @Test
    public void getUser_Success() throws ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException {
        LinkedHashMap<String, String> inputMap = new LinkedHashMap<>();
        ResponseEntity responseEntity = new ResponseEntity(inputMap, HttpStatus.OK);
        inputMap.put("display_name", "swetha");
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity);

        LinkedHashMap<String, String> resultMap  = new LinkedHashMap<>();
        resultMap.put("display_name", "swetha");
        Assert.assertEquals(userService.getUser("00"),resultMap);
        logger.info("The value is "+resultMap);
    }

    @Test
    public void getUser_NoContent() throws NoTrackException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException, ForbiddenException {
        LinkedHashMap<String, String> inputMap= new LinkedHashMap<>();
        ResponseEntity response = new ResponseEntity(inputMap,HttpStatus.NO_CONTENT);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenReturn(response);

        String Message = null;
        try{
            userService.getUser("00");
        }catch (Exception e){
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.NoUserException_Msg, Message);
    }

    @Test
    public void getTopTracks_Forbidden() {
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenThrow(HttpClientErrorException.Forbidden.class);
        String Message = null;
        try{
            userService.getUser("00");
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
            userService.getUser("00");
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
            userService.getUser("00");
        } catch (Exception e) {
            Message = e.getMessage();
        }
        Assert.assertEquals(AppConstants.NotFoundException_Msg, Message);
    }

    @Test
    public void getUsername_Success() throws ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException {
        LinkedHashMap<String, String> resultMap = new LinkedHashMap<>();
        ResponseEntity responseEntity = new ResponseEntity(resultMap, HttpStatus.OK);
        resultMap.put("display_name", "swetha");
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity);
        String str = userService.getUsername("00");
        logger.info("The value is "+str);
    }


}
