package com.THEmans.street_stall.Controller;

import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Dto.User_logout;
import com.THEmans.street_stall.Service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController //restcontrller를 사용하면 기본으로 하위에 이는 메소드는 전부 @responsebody를 가지게 된다.
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    /**
     * 로그인,회원가입
     */
    @PostMapping("/login")
    public User Login(@RequestBody User request){
        return loginService.saveUser(request);
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping("/logout")
    public User_logout Logout(@RequestBody User_logout user_logout){
        return loginService.logout(user_logout);
    }
}
