package com.spotify.faceapi.Service;

import com.spotify.faceapi.entity.UserInfo;
import com.spotify.faceapi.exception.*;
import com.spotify.faceapi.repository.UserRepository;
import com.spotify.faceapi.service.AppUserService;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import se.michaelthelin.spotify.exceptions.detailed.ForbiddenException;

import java.io.IOException;


@RunWith(MockitoJUnitRunner.class)
public class AppUserServiceTest {
    @InjectMocks
    private AppUserService appUserService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void validateUser_Success() throws NoArtistsException, NoAttributeException {

        UserInfo user=new UserInfo();
        user.setId(1);
        user.setUsername("test_uname");
        user.setPassword("test_pwd");
        user.setSpotifyUsername("test_uname");

        Mockito.when(userRepository.findByUsername("test_uname")).thenReturn(user);
        Mockito.when(appUserService.findByUsername("test_uname")).thenReturn(user);
        Assert.assertTrue(appUserService.validateUser("test_uname","test_pwd"));
    }

    @Test
    public void validateUser_Failure() throws NoArtistsException, NoAttributeException {

        UserInfo user=new UserInfo();
        user.setId(1);
        user.setUsername("test_uname");
        user.setPassword("test_pwd");
        user.setSpotifyUsername("test_uname");

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
        user.setSpotifyUsername("test_uname");

        Mockito.when(userRepository.save(user)).thenReturn(user);
        user_response =  appUserService.saveUser(user);
        Assert.assertEquals(user,user_response);
    }


}
