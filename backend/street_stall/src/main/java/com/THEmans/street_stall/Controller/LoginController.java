package com.THEmans.street_stall.Controller;

import com.THEmans.street_stall.Domain.KakaoAccessToken;
import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Jwt.JwtService;
import com.THEmans.street_stall.Repository.UserRepository;
import com.THEmans.street_stall.kakaologin.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {



//   /**
//     * JWT를 이용한 카카오 로그인
//     * @param code
//     * @return
//     */
//    @GetMapping("/api/oauth/token/kakao")
//    public Map<String,String> KakaoLogin(@RequestParam("code") String code){
//
//        //acess 토큰 받기
//        KakaoAccessToken oauthToken = oAuthService.getAccessTokenByCode(code);
//
//        //사용자 정보받기 및 회원가입
//        User saveUser = oAuthService.saveUser(oauthToken.getAccess_token());
//
//        //jwt토큰 저장
//        //JwtToken jwtTokenDTO = j
//
//    }
}
