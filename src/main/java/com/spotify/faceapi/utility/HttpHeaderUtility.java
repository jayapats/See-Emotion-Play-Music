package com.spotify.faceapi.utility;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;

public class HttpHeaderUtility {
    public HttpEntity<String> setHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>("paramters", headers);
        return entity;
    }
}
