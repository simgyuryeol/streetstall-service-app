package com.THEmans.street_stall.Service;

import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Dto.User_logout;
import com.THEmans.street_stall.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final UserRepository userRepository;

    //회원가입 서비스
    public User saveUser(User user){
        User finduser = userRepository.findByUserid(user.getUserid());

        if(finduser==null) {
            finduser = User.builder()
                    .userid(user.getUserid())
                    .nickname(user.getNickname())
                    .userstate(0L)
                    .guest(Boolean.TRUE)
                    .stall(Boolean.FALSE)
                    .manager(Boolean.FALSE)
                    .build();
            userRepository.save(finduser);
        }
        return finduser;
    }

    //로그아웃 서비스
    public User_logout logout(User_logout user_logout) {

        User_logout user = new User_logout();
        user.setUserid(user_logout.getUserid());
        user.setNickname(user_logout.getNickname());
        userRepository.deleteById(user_logout.getUserid());
        return user;
    }

//
//
//    /**
//     * json response 부분 따로 분리하기
//     */
//    //로그인시 응답 json response
//    public Map<String, String> successLoginResponse(String userid,String nickname){
//        Map<String, String> map = new LinkedHashMap<>();
//        //map.put("status", "200");
//        map.put("userid",userid);
//        map.put("nickname",nickname);
//        map.put("message","회원 가입 성공.");
//        return map;
//    }
//
//    //로그아웃시 응답 json response
//    public Map<String,String> successLogoutResponse(){
//        Map<String, String> map = new LinkedHashMap<>();
//        map.put("status","200");
//        map.put("message","로그아웃이 완료되었습니다.");
//        return map;
//    }

}
