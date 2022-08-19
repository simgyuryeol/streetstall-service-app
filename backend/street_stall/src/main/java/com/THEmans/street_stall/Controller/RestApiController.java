package com.THEmans.street_stall.Controller;

import com.THEmans.street_stall.Domain.KakaoAccessToken;
import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Jwt.JwtService;
import com.THEmans.street_stall.Jwt.JwtToken;
import com.THEmans.street_stall.Repository.UserRepository;
import com.THEmans.street_stall.Security.SecurityConfig;
import com.THEmans.street_stall.kakaologin.OAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
/**
 * 테스트용 컨트롤러
 */
public class RestApiController {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    //private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;
    private final OAuthService oAuthService; //카카오 서비스스

    @PostMapping("/join")
    public String join(@ModelAttribute User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("USER");
        user.setCreateTime(LocalDateTime.now());
        userRepository.save(user);

        return "회원가입완료";
    }



    @GetMapping("/api/v1/user")
    public String test1(){
        return "success";
    }
    @GetMapping("/api/v1/manager")
    public String test2(){
        return "success";
    }

    @GetMapping("/api/v1/admin")
    public String test3(){
        return "success";
    }


       /**
     * JWT를 이용한 카카오 로그인
     * @param code
     * @return
     */
    @GetMapping("/api/oauth/token/kakao")
    public Map<String,String> KakaoLogin(@RequestParam("code") String code){

        //acess 토큰 받기
        KakaoAccessToken oauthToken = oAuthService.getAccessTokenByCode(code);

        //사용자 정보받기 및 회원가입
        User saveUser = oAuthService.saveUser(oauthToken.getAccess_token());

        //jwt토큰 저장
        JwtToken jwtTokenDTO = jwtService.joinJwtToken(saveUser.getUserid());

        return jwtService.successLoginResponse(jwtTokenDTO);

    }

    //test로 직접 인가 코드 받기
    @GetMapping("/login/oauth2/code/kakao")
    public String KakaoCode(@RequestParam("code") String code) {
        return "카카오 로그인 인증완료, code: "  + code;
    }

    /**
     * refresh token 재보급
     */
    @GetMapping("/refresh/{userId}")
    public Map<String,String> refreshToken(@PathVariable("userId") String userid, @RequestHeader("refreshToken") String refreshToken,
                                           HttpServletResponse response) throws JsonProcessingException{
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        JwtToken jwtToken = jwtService.validRefreshToken(userid,refreshToken);
        Map<String,String> jsonResponse = jwtService.recreateTokenResponse(jwtToken);

        return jsonResponse;
    }
}
