package com.THEmans.street_stall.Controller;

import com.THEmans.street_stall.Domain.Member;
import com.THEmans.street_stall.Session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public Object homeLogin(@SessionAttribute(name= SessionConst.LOGIN_MEMBER,required = false) Member loginMember){
        if(loginMember==null){
            return null;

        }
        return loginMember;
    }
}
