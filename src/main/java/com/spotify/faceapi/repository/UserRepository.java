package com.spotify.faceapi.repository;

import com.spotify.faceapi.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {
  UserInfo findByUsername(String username);
}
