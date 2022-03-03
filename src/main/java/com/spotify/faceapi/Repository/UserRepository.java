package com.spotify.faceapi.Repository;

import com.spotify.faceapi.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo,Integer> {
    UserInfo findByUsername(String username);
}




