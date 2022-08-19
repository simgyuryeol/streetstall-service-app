package com.THEmans.street_stall.Jwt;

public interface JwtProperties {
    String SECRET = "심규렬"; //우리ㅣ 서버만 알고 있는 비밀값, JWT 암호화 복호화에 쓰이는 키
    int AccessToken_TIME = 60000; //(1/1000초)
    int RefreshToken_TIME = 200000;
    String HEADER_STRING = "accessToken";
}
