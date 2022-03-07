package com.spotify.faceapi.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@JsonPropertyOrder({
        "SaveTrackIdList"
})
@Generated("jsonschema2pojo")
public class SaveTrackList {

    @JsonProperty("SaveTrackIdList")
    private ArrayList<String> saveTrackIdList;

    @JsonProperty("SaveTrackIdList")
    public ArrayList<String> getSaveTrackIdList() {
        return saveTrackIdList;
    }

    @JsonProperty("SaveTrackIdList")
    public void setSaveTrackIdList(ArrayList<String> saveTrackIdList) {
        this.saveTrackIdList = saveTrackIdList;
    }


}