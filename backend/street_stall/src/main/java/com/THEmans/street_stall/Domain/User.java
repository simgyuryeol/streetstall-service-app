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

    /**
     * 0이면 Guest, 1이면 Stall, 2이면 Manager
     */
    private Long userstate;

    private Boolean guest;
    private Boolean stall;
    private Boolean manager;

    @Builder
    public User(String userid, String nickname, Long userstate, Boolean guest, Boolean stall, Boolean manager){
        this.userid=userid;
        this.nickname=nickname;
        this.userstate=userstate;
        this.guest=guest;
        this.stall=stall;
        this.manager=manager;
    }

}
