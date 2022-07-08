package com.THEmans.street_stall.Dto;

import lombok.Getter;

@Getter
public class MemberSginUpDto {
    private String id;
    private String nickname;
    private String password;
    private String user_state;
    private Boolean Guest;
    private Boolean Stall;
    private Boolean Manager;
}
