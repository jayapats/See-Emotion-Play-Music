package com.spotify.faceapi.service;

import com.spotify.faceapi.utility.AppConstants;
import com.spotify.faceapi.utility.EncoderUtility;
import com.spotify.faceapi.utility.EncryptUtility;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;


@Data
@RequiredArgsConstructor
@Service
public class SpotifyService {
    private String codeVerifier;

    public String getAuthorizationURL() {
        final var codeVerifier = EncoderUtility.generate();
        setCodeVerifier(codeVerifier);
        System.out.println("Authorization URL: ");
        System.out.println("https://accounts.spotify.com/en/authorize?client_id=\" + AppConstants.CLIENT_ID\n" +
                "                + \"&response_type=code&redirect_uri=\" + AppConstants.REDIRECT_URI\n" +
                "                + \"&code_challenge_method=S256&code_challenge=\" + EncryptUtility.generate(codeVerifier)\n" +
                "                + \"&scope=ugc-image-upload,user-read-playback-state,user-modify-playback-state,user-read-currently-playing,streaming,app-remote-control,user-read-email,user-read-private\"\n" +
                "                + \",playlist-read-collaborative,playlist-modify-public,playlist-read-private,playlist-modify-private,user-library-modify,user-library-read,user-top-read,user-read-playback-position,user-read-recently-played,user-follow-read,user-follow-modify\"");

        return "https://accounts.spotify.com/en/authorize?client_id=" + AppConstants.CLIENT_ID
                + "&response_type=code&redirect_uri=" + AppConstants.REDIRECT_URI
                + "&code_challenge_method=S256&code_challenge=" + EncryptUtility.generate(codeVerifier)
                + "&scope=ugc-image-upload,user-read-playback-state,user-modify-playback-state,user-read-currently-playing,streaming,app-remote-control,user-read-email,user-read-private"
                + ",playlist-read-collaborative,playlist-modify-public,playlist-read-private,playlist-modify-private,user-library-modify,user-library-read,user-top-read,user-read-playback-position,user-read-recently-played,user-follow-read,user-follow-modify";

    }
}