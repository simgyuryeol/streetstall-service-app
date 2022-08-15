package com.THEmans.street_stall.Controller;

import com.THEmans.street_stall.Domain.Member;
import com.THEmans.street_stall.Dto.LoginDto;
import com.THEmans.street_stall.Dto.MemberSginUpDto;
import com.THEmans.street_stall.Repository.MemberRepository;
import com.THEmans.street_stall.Service.MemberService;
import com.THEmans.street_stall.Session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    //로그인
    @PostMapping("/login")
    public Member login(@RequestBody LoginDto loginDto, HttpServletRequest request){
        Member loginmember = memberService.login(loginDto.getLoginId(), loginDto.getPassword());

        if (loginmember == null){
            return null;
        }
//        Cookie idCookie = new Cookie("memberId",String.valueOf(loginmember.getId()));
//        response.addCookie(idCookie);
        // 로그인 성공 처리, 세션이 잇으면 세션 반환 없으면 신규 세션을 생성해서 반환
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginmember);

        return loginmember;

    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session!=null){
            session.invalidate();
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
