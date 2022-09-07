package com.THEmans.street_stall.Service;

import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final UserRepository userRepository;


    public Map<String,String> saveUser(String userid, String nickname){
        User user = userRepository.findByUserid(userid);

        if(user==null) {
            user = User.builder()
                    .userid(userid)
                    .nickname(nickname)
                    .build();
            userRepository.save(user);
        }
        return successLoginResponse(userid,nickname);
    }

    public Map<String,String> logout(String userid) {

        userRepository.deleteById(userid);
        return successLogoutResponse();
    }



    /**
     * json response 부분 따로 분리하기
     */
    //로그인시 응답 json response
    public Map<String, String> successLoginResponse(String userid,String nickname){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("status", "200");
        map.put("userid",userid);
        map.put("nickname",nickname);
        map.put("message","회원 가입 성공.");
        return map;
    }

    //로그아웃시 응답 json response
    public Map<String,String> successLogoutResponse(){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("status","200");
        map.put("message","로그아웃이 완료되었습니다.");
        return map;
    }

}
