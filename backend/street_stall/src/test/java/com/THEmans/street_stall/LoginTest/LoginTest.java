package com.THEmans.street_stall.LoginTest;

import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Repository.UserRepository;
import com.THEmans.street_stall.Service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@Slf4j
public class LoginTest {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestRestTemplate restTemplate; //API 테스트
    @LocalServerPort //Random_Port의 port값을 받아온다.
    private int port;


    @AfterEach //테스트 종료후 DB 초기화
    public void clean(){
        userRepository.deleteAll();
    }

    /**
     * 회원가입 서비스 테스트
     * 정상적으로 DB에 저장이 되는지
     */
    @Test
    @DisplayName("회원가입 성공")
    //@Transactional
    void 회원가입성공(){
        //given
        String userid="123";
        String nickname="심규렬";
        //when
        loginService.saveUser(userid,nickname);
        User user=userRepository.findByUserid(userid);
        //then
        Assertions.assertThat(user.getNickname()).isEqualTo(nickname);
    }

    /**
     * 중복 회원가입 방지 테스트
     */
    @Test
    @DisplayName("중복 회원가입 방지 테스트")
    //@Transactional
    void 중복회원가입방지테스트(){
        //given
        String userid="123";
        String nickname="심규렬";
        String userid2="123";
        String nickname2="심규";
        //when
        loginService.saveUser(userid,nickname);
        User user=userRepository.findByUserid(userid);
        //then
        Assertions.assertThat(user.getNickname()).isEqualTo(nickname);
        loginService.saveUser(userid2,nickname2);
        Assertions.assertThat(user.getNickname()).isEqualTo(nickname);
    }

    /**
     * 로그인 contorller 테스트
     */
    @Test
    @DisplayName("/login API 테스트")
    //@Transactional
    public void loginAPI테스트(){
        //given
        String userid="123";
        String nickname="tlarbfuf";

        String url = "http://localhost:"+port+"/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("userid",userid);
        headers.set("nickname",nickname);
        HttpEntity request = new HttpEntity(headers);

        //when

        ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST,request,User.class);
        User response_user=response.getBody();
        //then
        Assertions.assertThat(response_user.getNickname()).isEqualTo(nickname);

    }

    /**
     * 로그아웃 테스트
     */
    @Test
    @DisplayName("로그아웃테스트")
    public void 로그아웃API테스트(){
        //given
        String userid="123";
        String nickname="tlarbfuf";
        loginService.saveUser(userid,nickname);


        String url = "http://localhost:"+port+"/logout";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("userid",userid);
        HttpEntity request = new HttpEntity(headers);

        //when
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.DELETE,request, Map.class);
        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



}
