package com.THEmans.street_stall.Domain;

import lombok.Data;

@Data
public class KakaoAccessToken {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;

    private String scope;
    private int refresh_token_expires_in;
}
