package com.spotify.faceapi.service;

import com.spotify.faceapi.entity.UserInfo;
import com.spotify.faceapi.exception.SpotifyException;
import com.spotify.faceapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppUserService {
  @Autowired private UserRepository userRepository;

  public boolean validateUser(String username, String inputPassword) {
    UserInfo user = findByUsername(username);
    boolean getpassword = true;
    if (inputPassword.equals(user.getPassword())) {
      return getpassword;
    }

    return false;
  }

  public UserInfo saveUser(UserInfo userInfo) throws SpotifyException {
    try {
      return userRepository.save(userInfo);
    } catch (DataAccessException e) {
      throw new SpotifyException("Can't create user, username already exist!");
    } catch (Exception e) {
      throw (e);
    }
  }

  public List<UserInfo> saveUsers(List<UserInfo> userinfo) {
    return userRepository.saveAll(userinfo);
  }

  public List<UserInfo> getUsers() {
    return userRepository.findAll();
  }

  public UserInfo getUserById(int id) {
    return userRepository.findById(id).orElse(null);
  }

  public UserInfo findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public String deleteUser(int id) {
    userRepository.deleteById(id);
    return "User removed !! " + id;
  }

  public UserInfo updateUser(UserInfo userInfo) {
    UserInfo existingUser = userRepository.findById(userInfo.getId()).orElse(null);
    if (existingUser != null) {
      existingUser.setUsername(userInfo.getUsername());
      existingUser.setPassword(userInfo.getPassword());
      existingUser.setSpotifyUsername(userInfo.getSpotifyUsername());
      return userRepository.save(existingUser);
    }
    return existingUser;
  }
}
