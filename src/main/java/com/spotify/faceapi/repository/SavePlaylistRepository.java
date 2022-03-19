package com.spotify.faceapi.repository;

import com.spotify.faceapi.entity.SaveGeneratedPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavePlaylistRepository extends JpaRepository<SaveGeneratedPlaylist, Integer> {
}
