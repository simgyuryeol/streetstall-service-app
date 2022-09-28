package com.THEmans.street_stall.Controller;

import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    /**
     * 로그인,회원가입
     */
    @PostMapping("/login")
    public Map<String, String> Login(@RequestHeader("userid")String userid, @RequestHeader("nickname")String nickname){
        return loginService.saveUser(userid, nickname);
    }

    /**
     * 로그아웃
     */
    @DeleteMapping("/logout")
    public Map<String, String> Logout(@RequestHeader("userid")String userid){
        return loginService.logout(userid);
    }
}
