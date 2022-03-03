package com.spotify.faceapi.Service;

import com.spotify.faceapi.Entity.UserInfo;
import com.spotify.faceapi.Exception.*;
import com.spotify.faceapi.Repository.UserRepository;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@RunWith(MockitoJUnitRunner.class)
public class AppUserServiceTest {
    @InjectMocks
    private AppUserService appUserService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void validateUser_Success() throws NoAccountException, ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException, IOException, NoAttributeException, ParseException {

        UserInfo user=new UserInfo();
        user.setId(1);
        user.setUsername("test_uname");
        user.setPassword("test_pwd");
        user.setSpotify_username("test_uname");

        Mockito.when(userRepository.findByUsername("test_uname")).thenReturn(user);
        Mockito.when(appUserService.findByUsername("test_uname")).thenReturn(user);
        Assert.assertTrue(appUserService.validateUser("test_uname","test_pwd"));
    }

    @Test
    public void validateUser_Failure() throws NoAccountException, ForbiddenException, NoArtistsException, BadRequestException, NotFoundException, UnAuthorizedException, IOException, NoAttributeException, ParseException {

        UserInfo user=new UserInfo();
        user.setId(1);
        user.setUsername("test_uname");
        user.setPassword("test_pwd");
        user.setSpotify_username("test_uname");

        Mockito.when(userRepository.findByUsername("test_uname")).thenReturn(user);
        Mockito.when(appUserService.findByUsername("test_uname")).thenReturn(user);
        Assert.assertTrue(!appUserService.validateUser("test_uname","wrong_pass"));
    }

    @Test
    public void saveUser_Success() throws Exception {

        UserInfo user=new UserInfo();
        UserInfo user_response=new UserInfo();
        user.setId(1);
        user.setUsername("test_uname");
        user.setPassword("test_pwd");
        user.setSpotify_username("test_uname");

        Mockito.when(userRepository.save(user)).thenReturn(user);
        user_response =  appUserService.saveUser(user);
        Assert.assertEquals(user,user_response);
    }


}
