package com.spotify.faceapi.Controller;

import com.spotify.faceapi.Entity.UserInfo;
import com.spotify.faceapi.Exception.BadArgumentsException;
import com.spotify.faceapi.Exception.InternalException;
import com.spotify.faceapi.Exception.ResourceNotFoundException;
import com.spotify.faceapi.Service.AppUserService;
import com.spotify.faceapi.Utility.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.spotify.faceapi.Service.SpotifyService;


import org.apache.hc.core5.http.ParseException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

@Controller
@AllArgsConstructor
@Validated
public class MainController {

    @Autowired
    private AppUserService appuserService;

    private final SpotifyService spotifyService;

    @GetMapping("/")
    public String main(Model model) {

        return "login";
    }

    @GetMapping("/register_user")
    public String regUser(Model model) {

        return "register_user";
    }

    @PostMapping("/addUser")
//    public String addUser(@Valid @NotBlank @RequestParam String username, @Valid @NotBlank @RequestParam String password, @Valid @NotBlank @RequestParam String spotify_username) {
        public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String spotify_username) throws Exception {
        UserInfo userInfo = new UserInfo();
        UserInfo response = new UserInfo();
        if(!username.isEmpty() && !password.isEmpty() && !spotify_username.isEmpty()){
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setSpotify_username(spotify_username);
            response = appuserService.saveUser(userInfo);
            System.out.println("response: "+response);
            if(response.getUsername().equals(username)){
                return "login";
            }
        }
            return "register_user_failure";

    }


    @GetMapping(value="/validateLogin", produces = MediaType.TEXT_HTML_VALUE)
    public String validateLogin(@RequestParam String username,  @RequestParam String password,Model model) {
        System.out.println("validateLogin!!!!");
        if(appuserService.validateUser(username, password)){
            System.out.println("spotifyService.getAuthorizationURL() :");
            System.out.println(spotifyService.getAuthorizationURL());
            model.addAttribute("url", spotifyService.getAuthorizationURL());
            return "home";
        }
        else{
            return "login_failure";
        }

    }

    @GetMapping(value = "/validate", produces = MediaType.TEXT_HTML_VALUE)
    public String mainWithParam(@RequestParam("username") String username, @RequestParam("password") String password,Model model) {

        String s ="login";
        if(username.equalsIgnoreCase("swetha") && password.equalsIgnoreCase("swetha"))
            s = "home";
        System.out.println("spotifyService.getAuthorizationURL() :");
        System.out.println(spotifyService.getAuthorizationURL());
            model.addAttribute("url", spotifyService.getAuthorizationURL());
        return s;
    }

    @GetMapping(value = "/faceapi", produces = MediaType.TEXT_HTML_VALUE)
    public String faceAPIHandler(
            Model model) {

        System.out.println("faceAPIHandler called");
        String s ="faceapi";

        return s;
    }

    @GetMapping(value = "/logout", produces = MediaType.TEXT_HTML_VALUE)
    public String logoutHandler(final HttpSession session) {
        session.invalidate();
        return "redirect:/?logout";
    }

    public String showIndex(final Model model) {
        model.addAttribute("url", spotifyService.getAuthorizationURL());
        return "home";
    }

    @GetMapping("/exception/{exception_id}")
    public void getSpecificException(@PathVariable("exception_id") String pException) {
        if("not_found".equals(pException)) {
            throw new ResourceNotFoundException("resource not found");
            //not_found - response code 404
        }
        else if("bad_arguments".equals(pException)) {
            throw new BadArgumentsException("bad arguments");
            //bad_arguments - response code 400
        }
        else {
            throw new InternalException("internal error");
            //any other - response code 500
        }
    }

    @GetMapping("/exception/throw")
    public void getException() throws Exception {
        throw new Exception("error");
    }


    }
