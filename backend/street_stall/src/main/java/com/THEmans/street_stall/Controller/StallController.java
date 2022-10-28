package com.THEmans.street_stall.Controller;

import com.THEmans.street_stall.Domain.Stall;
import com.THEmans.street_stall.Dto.Stall_create;
import com.THEmans.street_stall.Service.StallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        log.info(stallCreate.toString());
        return stallService.StallCreate(stallCreate);
    }
}
