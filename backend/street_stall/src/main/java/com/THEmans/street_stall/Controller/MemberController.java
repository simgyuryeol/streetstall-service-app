package com.THEmans.street_stall.Controller;

import com.THEmans.street_stall.Dto.MemberSginUpDto;
import com.THEmans.street_stall.Repository.MemberRepository;
import com.THEmans.street_stall.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    //회원가입
    @PostMapping("/users")
    public ResponseEntity SignUp(@RequestBody MemberSginUpDto memberSginUpDto){

        if (memberRepository.findById(memberSginUpDto.getId()).isPresent()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        memberService.SignUp(memberSginUpDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
