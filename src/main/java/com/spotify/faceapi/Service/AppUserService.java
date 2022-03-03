package com.spotify.faceapi.Service;

import com.spotify.faceapi.Entity.UserInfo;
import com.spotify.faceapi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppUserService {
    @Autowired
    private UserRepository userRepository;

    public boolean validateUser(String username, String input_password) {
        UserInfo user = findByUsername(username);
       if(input_password.equals(user.getPassword()))
            return true;
       else
           return false;
    }

    public UserInfo saveUser(UserInfo userInfo) throws Exception {
        try {
        return userRepository.save(userInfo);
        } catch (DataAccessException e) {
            throw new Exception("Can't create user, username already exist!");
        }
        catch (Exception e) {
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
        existingUser.setUsername(userInfo.getUsername());
        existingUser.setPassword(userInfo.getPassword());
        existingUser.setSpotify_username(userInfo.getSpotify_username());
        return userRepository.save(existingUser);
    }


}
