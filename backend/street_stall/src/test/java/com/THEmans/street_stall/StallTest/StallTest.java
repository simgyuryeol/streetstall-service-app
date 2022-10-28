package com.THEmans.street_stall.StallTest;

import com.THEmans.street_stall.Domain.Stall;
import com.THEmans.street_stall.Repository.StallRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class StallTest {
    @Autowired
    StallRepository stallRepository;

    @Test
    public void stallTest(){
        log.info(stallRepository.findAll().toString());
    }
}
