package com.spotify.faceapi.Repository;

import com.spotify.faceapi.Entity.SaveGeneratedPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavePlaylistRepository extends JpaRepository<SaveGeneratedPlaylist,Integer> {

}


