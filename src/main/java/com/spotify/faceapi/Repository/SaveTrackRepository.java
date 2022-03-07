package com.spotify.faceapi.Repository;

import com.spotify.faceapi.Entity.SaveTrackID;
import com.spotify.faceapi.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveTrackRepository extends JpaRepository<SaveTrackID,Integer>{
//    UserInfo findByUsername(String username);
}
