package com.THEmans.street_stall.Domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    private String userid;

    private String nickname;

    @Builder
    public User(String userid, String nickname){
        this.userid=userid;
        this.nickname=nickname;
    }

}
