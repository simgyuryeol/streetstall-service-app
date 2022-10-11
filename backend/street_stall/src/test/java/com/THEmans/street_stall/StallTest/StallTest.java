package com.THEmans.street_stall.StallTest;

import com.THEmans.street_stall.Repository.UserRepository;
import com.THEmans.street_stall.Service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class StallTest {

    @Autowired
    private LoginService stallService; //가게 서비스
    @Autowired
    private UserRepository stallRepository; //가게 레포지토리


    /**
     * 노점 등록
     * 1. 처음이면 새로운 노점이 등록된다.
     * 2. 두번째면 이전 노점이 삭제되고 새로운 노점이 등록된다.?
     */
    @Test
    @DisplayName("노점 등록")
    void 노점등록(){
        //given
        String stall_id; //가게 id
        String stall_name; //가게 이름
        String explanation; //가게 설명
        boolean open; //가게 오픈 유무
        String location; //가게 위치, 위도,경도?
        //when

        //then
    }

    /**
     * 노점 위치 찾기
     * 노점의 위치를 자동으로 찾아준다.
     */
    @Test
    @DisplayName("노점 위치")
    void 노점위치(){


    }

    /**
     * 노점 수정
     * 노점 정보가 수정된다
     */
    @Test
    @DisplayName("노점 수정")
    void 노점수정(){
        //given

        //when

        //then
    }


    /**
     * 노점 삭제
     * 노점을 삭제한다.
     */
    @Test
    @DisplayName("노점 삭제")
    void 노점삭제(){
        //given

        //when

        //then
    }


    /**
     * 영업 상태를 변경 (시작/종료)
     * 영업을 종료하면 노점에 대한 정보를 고객이 찾을 수 없다.
     * 영업을 시작하면 지도와 검색메뉴에 노점이 표시된다.
     */
    @Test
    @DisplayName("영업상태")
    void 영업상태(){
        //given

        //when

        //then
    }


    /**
     * 메뉴 등록
     * 새로운 메뉴를 등록한다.
     */
    @Test
    @DisplayName("메뉴 등록")
    void 메뉴등록(){
        //given

        //when

        //then
    }


    /**
     * 메뉴 수정
     * 메뉴를 수정한다.
     */
    @Test
    @DisplayName("메뉴 수정")
    void 메뉴수정(){
        //given

        //when

        //then
    }


    /**
     * 메뉴 삭제
     * 메뉴를 삭제한다.
     */
    @Test
    @DisplayName("메뉴 삭제")
    void 메뉴삭제(){
        //given

        //when

        //then
    }


}
