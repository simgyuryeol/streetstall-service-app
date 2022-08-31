package com.THEmans.street_stall.Controller;

import com.THEmans.street_stall.Domain.KakaoAccessToken;
import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Jwt.JwtService;
import com.THEmans.street_stall.Jwt.JwtToken;
import com.THEmans.street_stall.Jwt.RefreshTokenRespository;
import com.THEmans.street_stall.Repository.UserRepository;
import com.THEmans.street_stall.kakaologin.OAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final OAuthService oAuthService; //카카오 서비스스
    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * JWT를 이용한 카카오 로그인
     * @param code
     * @return
     */
    @GetMapping("/kakao/login")
    public Map<String,String> KakaoLogin(@RequestParam("code") String code){
        //acess 토큰 받기
        KakaoAccessToken oauthToken = oAuthService.getAccessTokenByCode(code);
        //사용자 정보받기 및 회원가입
        User saveUser = oAuthService.saveUser(oauthToken.getAccess_token());
        //jwt토큰 저장
        JwtToken jwtTokenDTO = jwtService.joinJwtToken(saveUser.getUserid());

        return jwtService.successLoginResponse(jwtTokenDTO);
    }

    /**
     * 로그아웃
     */

    @GetMapping("/kakao/logout")
    public Map<String,String> KakaoLogout(@RequestHeader("accessToken") String accessToken){
        String userid = jwtService.UserfindbyAccessToken(accessToken);
        User user = userRepository.findByUserid(userid);
        user.setJwtRefreshToken(null);

        userRepository.save(user);

        return jwtService.successLogoutResponse();
    }

    /**
     * accestoken., refreshtoken 갱신
     */
    @GetMapping("/refresh/{userId}")
    public Map<String, String> refreshToken(@PathVariable("userId") String userid, @RequestHeader("refreshToken")String refreshToken,
                                            HttpServletResponse response) throws JsonProcessingException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        return jwtService.RefreshTokenCheck(userid,refreshToken);
        //refreshtoken이 없으면 로그인 요청, 있으면 accesstoken 재발급
    }





}
