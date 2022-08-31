package com.THEmans.street_stall.login;

import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Jwt.JwtProperties;
import com.THEmans.street_stall.Jwt.JwtService;
import com.THEmans.street_stall.Jwt.JwtToken;
import com.THEmans.street_stall.Repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //Random_Port를 사용해야 TestRestTemplate를 사용할 수 잇다.
@AutoConfigureMockMvc
@Log4j2
public class KakaoApiServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TestRestTemplate restTemplate; //API 테스트
    @LocalServerPort //Random_Port의 port값을 받아온다.
    private int port;


    @Test
    @Transactional
    @DisplayName("카카오 계정 로그인 잘 되는지 확인")
    void KakaoApiLgoin(){
        User user = User.builder()
                .userid("12345")
                .name("심규렬")
                .email("abc@kakao.com")
                .roles("User") //추후 변경
                .build();
        userRepository.save(user);
        Assertions.assertThat(user.getUserid()).isEqualTo("12345");
    }

    @Test
    @Transactional
    @DisplayName("JWT 토큰 발급 확인")
    void JWTTokenCreate(){
        User user = User.builder()
                .userid("12345")
                .name("심규렬")
                .email("abc@kakao.com")
                .roles("User") //추후 변경
                .build();
        userRepository.save(user);
        jwtService.joinJwtToken(user.getUserid());

        //Refresh token 생성
        String refreshToken = JWT.create()
                .withSubject(user.getUserid())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.RefreshToken_TIME))
                .withClaim("id",user.getId())
                .withClaim("userid",user.getUserid())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        Assertions.assertThat(user.getJwtRefreshToken().getRefreshToken()).isEqualTo(refreshToken);
    }

    @Test
    @Transactional
    @DisplayName("JWT 토큰 유효성 검사")
    void JWTTokenAuth(){
        User user = User.builder()
                .userid("12345")
                .name("심규렬")
                .email("abc@kakao.com")
                .roles("User") //추후 변경
                .build();
        userRepository.save(user);

        JwtToken jwttoken = jwtService.joinJwtToken(user.getUserid());

        String token_Auth= jwtService.validAccessToken(jwttoken.getAccessToken());

        Assertions.assertThat(token_Auth).isEqualTo(user.getUserid());
    }

//    @Test
//    @Transactional
//    @DisplayName("JWT 토큰 제거 확인")
//    void JWTTokenDelete(){
//        User user = User.builder()
//                .userid("12345")
//                .name("심규렬")
//                .email("abc@kakao.com")
//                .roles("User") //추후 변경
//                .build();
//        userRepository.save(user);
//
//        JwtToken jwttoken = jwtService.joinJwtToken(user.getUserid());
//
//        String url = "http://localhost:"+port+"/kakao/logout";
//
//        HttpHeaders headers = new HttpHeaders();
//        //headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
//        //headers.setContentType(MediaType.APPLICATION_JSON);
//        //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
////        headers.set("accessToken",jwttoken.getAccessToken());
////        HttpEntity request = new HttpEntity(headers);
//
//        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null,Map.class,jwttoken.getAccessToken());
//
//        //when
//        log.info(port);
//        log.info("answer:"+response.getStatusCode());
//
//    }



}
