package com.spotify.faceapi.Service;

import com.spotify.faceapi.Utility.AppConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import com.spotify.faceapi.DTO.AccessTokenDTO;

@Service
@RequiredArgsConstructor
public class AccessTokenService {
//    private final AppConstants appConstants;
    private final SpotifyService spotifyService;
    private final RestTemplate restTemplate;
    private static final String URL = "https://accounts.spotify.com/api/token";


    public String getToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", AppConstants.CLIENT_ID);
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", AppConstants.REDIRECT_URI);
        map.add("code_verifier", spotifyService.getCodeVerifier());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<AccessTokenDTO> response = restTemplate.postForEntity(URL, request, AccessTokenDTO.class);
        return response.getBody().getAccess_token();
    }

}
