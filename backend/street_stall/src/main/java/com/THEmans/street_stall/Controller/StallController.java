package com.THEmans.street_stall.Controller;

import com.THEmans.street_stall.Domain.Stall;
import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Dto.Stall_correction;
import com.THEmans.street_stall.Dto.Stall_create;
import com.THEmans.street_stall.Service.StallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController //restcontrller를 사용하면 기본으로 하위에 이는 메소드는 전부 @responsebody를 가지게 된다.
@RequiredArgsConstructor
@Slf4j
public class StallController {

    private final StallService stallService;

    /**
     * 노점 생성
     */
    @PostMapping("/stall")
    public Stall stallcreate(@RequestBody Stall_create stallCreate){
        return stallService.StallCreate(stallCreate);
    }

    /**
     * 노점 수정
     */
    @PutMapping("/stall/{id}")
    public Stall stallcorrection(@PathVariable Long id,@RequestBody Stall_correction stall_correction){
        return stallService.StallCorrection(id,stall_correction);
    }

    /**
     * 노점 삭제
     */
    @DeleteMapping("/stall/{id}")
    public User stalldelete(@PathVariable Long id){
        return stallService.StallDelete(id);
    }

    /**
     * 노점 오픈, 닫기
     */
    @PutMapping("/stall/open/{id}")
    public Stall stallopen(@PathVariable Long id, @RequestBody Map<String,Boolean> stallopen){
        return stallService.StallOpen(id,stallopen);
    }

    /**
     * 가게 찾기
     */
    @GetMapping("/stall/find")
    public List<Stall> stallsearch(@RequestBody Map<String,String> stallname){
        return stallService.StallSearch(stallname);
    }

    /**
     * 가게 위치 찾기
     */
    @GetMapping("/stall/location")
    public List<Stall> stalllocation(){
        return stallService.StallLocation();
    }

}
