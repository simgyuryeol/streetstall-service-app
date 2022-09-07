package com.THEmans.street_stall.Jwt;

import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 실제 jwt 토큰과 관련된 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class JwtService {
    private final JwtProviderService jwtProviderService;
    private final UserRepository userRepository;

    /**
     * acess, refresh 토큰 생성
     */
    @Transactional
    public JwtToken joinJwtToken(String userId){
        User user = userRepository.findByUserid(userId);
        RefreshToken userRefreshToken = user.getJwtRefreshToken();

        //처음 서비스를 이용하는 사용자(refresh 토큰이 없는 사용자)
        if(userRefreshToken ==null){
            //access, refresh 토큰 생성
            JwtToken jwtToken = jwtProviderService.createJwtToken(user.getId(),user.getUserid());

            //refreshToken 생성
            RefreshToken refreshToken = new RefreshToken(jwtToken.getRefreshToken());

            //DB에 저장(refresh 토큰 저장)
            user.createRefreshToken(refreshToken);

            return jwtToken;
        }
        else { //refresh토큰이 있는 사용자(기존 사용자)
            String accessToken = jwtProviderService.validRefreshToken(userRefreshToken);

            //refresh토큰 기간 유효
            if(accessToken != null){
                return new JwtToken(accessToken, userRefreshToken.getRefreshToken());
            }
            else{ //refresh 토큰ㄴ 기간 만료. 새로운 access, refresh 토큰 새엇ㅇ
                JwtToken newJwtToken = jwtProviderService.createJwtToken(user.getId(), user.getUserid());

                user.SetRefreshToken(newJwtToken.getRefreshToken());
                return newJwtToken;
            }
        }
    }

    /**
     * acess토큰 validate
     */
    public String validAccessToken(String accessToken){
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(accessToken);

            if(!verify.getExpiresAt().before(new Date())){ //만료기간 검증 verify.getExpriesAt()를 하면 만료되는 시간이 나온다
                return verify.getClaim("userid").asString();
            }
        }catch (Exception e){
            // 여기도 accesstoken이 기간 만료인지 정삭적이지 않은 accesstoken인지 구분 해야 하는가?
            return null;
        }
        return null;
    }

    /**
     * access token으로 유저 아이디 찾기 만료 여부 상관없이
     */

    public String UserfindbyAccessToken(String accessToken){
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(accessToken);
            return verify.getClaim("userid").asString();

        }catch (Exception e){
            // 여기도 accesstoken이 기간 만료인지 정삭적이지 않은 accesstoken인지 구분 해야 하는가?
            return null;
        }

    }

    /**
     * refresh 토큰 validate
     */
    @Transactional
    public JwtToken validRefreshToken(String userid, String refreshToken){

        User findUser = userRepository.findByUserid(userid);

        //전달받은 refresh 토큰과 DB의 refresh 토큰이 일치하는지 확인
        RefreshToken findRefreshToken = sameCheckRefreshToken(findUser,refreshToken);

        //refresh 토큰의 유효기간이 남아 access 토크난 생성
        if(findRefreshToken != null){
            //log.info("액세스만 변경");
            //refresh 토큰이 만료되지 않았으면 access 토큰이 null이 아니다.
            String accessToken = jwtProviderService.validRefreshToken(findRefreshToken);
            return new JwtToken(accessToken, refreshToken);
        }
        //refresh 토큰이 만료됨 -> 새롭게 로그인
        else {
            //log.info("리플레시도 변경 변경");
//            JwtToken newJwtToken = jwtProviderService.createJwtToken(findUser.getId() , findUser.getUserid());
//            findUser.SetRefreshToken(newJwtToken.getRefreshToken());
//            return newJwtToken;
            return null;
        }
    }

    /**
     * 리프레쉬 토큰 유효하면 accecc토큰 발급, 아니면 로그인 요청
     * @param userid
     * @param refreshToken
     */
    public Map<String, String> RefreshTokenCheck(String userid, String refreshToken) {

        JwtToken jwtToken = validRefreshToken(userid,refreshToken);

        if (jwtToken!=null) { //리프레쉬 토큰이 아직 유효해서 액세스토큰 재발급 받으면 해당 값을 클라이언트한테 전달

            Map<String, String> jsonResponse = recreateTokenResponse(jwtToken);
            return jsonResponse;
        }
        else{ //새롭게 로그인을 해야함
            return requiredJwtTokenResponse();
        }
    }


    private RefreshToken sameCheckRefreshToken(User findUser, String refreshToken) {
        //DB에서 찾기
        RefreshToken jwtRefreshToken = findUser.getJwtRefreshToken();

        if(jwtRefreshToken!=null && jwtRefreshToken.getRefreshToken().equals(refreshToken)){
            return jwtRefreshToken;
        }
        return null;
    }

    /**
     * json response 부분 따로 분리하기
     */
    //로그인시 응답 json response
    public Map<String, String> successLoginResponse(JwtToken jwtToken){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("status", "200");
        map.put("message","accessToken, refreshToken이 생성되었습니다.");
        map.put("accessToken", jwtToken.getAccessToken());
        map.put("refreshToken",jwtToken.getRefreshToken());
        return map;
    }

    //로그아웃시 응답 json response
    public Map<String,String> successLogoutResponse(){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("status","200");
        map.put("message","로그아웃이 완료되었습니다.");
        return map;
    }

    //인증 요구 json response (jwt 토큰이 필요한 요구)
    public Map<String,String> requiredJwtTokenResponse(){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("status","401");
        map.put("message","인증이 필요한 페이지 입니다. 로그인을 해주세요");
        return map;
    }

    //accessToekn이 만료된 경우의 response
    public Map<String,String> requiredRefreshTokenResponse(){
        Map<String,String> map = new LinkedHashMap<>();
        map.put("status","401");
        map.put("message","accessToekn이 만료되었거나 잘못된 값입니다.");
        return map;
    }

    //refresh 토큰 재발급 response
    public Map<String,String> recreateTokenResponse(JwtToken jwtToken){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("status","200");
        map.put("message","refresh, access 토큰이 재발급되었습니다.");
        map.put("accessToken",jwtToken.getAccessToken());
        map.put("refreshToken", jwtToken.getRefreshToken());
        return map;
    }


}
