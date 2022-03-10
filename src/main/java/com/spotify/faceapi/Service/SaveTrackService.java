package com.spotify.faceapi.Service;

import com.spotify.faceapi.Entity.SaveTrackID;
import com.spotify.faceapi.Entity.UserInfo;
import com.spotify.faceapi.Repository.SaveTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaveTrackService {
    @Autowired
    private SaveTrackRepository saveTrackRepository;

    public void saveTrackID(List<SaveTrackID> savetrackIDList) throws Exception {
        try {
//            SaveTrackID saveTrackID = new SaveTrackID();
            saveTrackRepository.deleteAll();
            saveTrackRepository.saveAll(savetrackIDList);
        } catch (DataAccessException e) {
            throw new Exception("Can't save TrackID to DB!");
        }
        catch (Exception e) {
            throw (e);
        }
    }

    public List<SaveTrackID> getTrackIDs() {
        return saveTrackRepository.findAll();
    }

}
