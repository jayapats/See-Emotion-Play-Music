package com.spotify.faceapi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@EnableAutoConfiguration
@Table(name = "USER_TBL")
public class UserInfo {
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String spotify_username;
    private String password;
}
