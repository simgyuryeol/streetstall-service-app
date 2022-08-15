package com.THEmans.street_stall.Service;

import com.THEmans.street_stall.Domain.Member;
import com.THEmans.street_stall.Dto.MemberSginUpDto;
import com.THEmans.street_stall.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Member SignUp(MemberSginUpDto memberSginUpDto){
        Member member=(Member.builder()
                .id(memberSginUpDto.getId())
                .password(memberSginUpDto.getPassword())
                .nickname(memberSginUpDto.getNickname())
                .user_state(memberSginUpDto.getUser_state())
                .Guest(memberSginUpDto.getGuest())
                .Stall(memberSginUpDto.getStall())
                .Manager(memberSginUpDto.getManager())
                .build()
        );
        return memberRepository.save(member);
    }

    //로그인 서비스
    public Member login(String loginId, String password){
        return memberRepository.findById(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
