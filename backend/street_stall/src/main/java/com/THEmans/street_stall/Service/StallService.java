package com.THEmans.street_stall.Service;

import com.THEmans.street_stall.Domain.Stall;
import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Dto.Stall_create;
import com.THEmans.street_stall.Repository.StallRepository;
import com.THEmans.street_stall.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StallService {
    private final StallRepository stallRepository;
    private final UserRepository userRepository;

    /**
     * 가게 생성
     */
    public Stall StallCreate(Stall_create stallCreate){
        User user=userRepository.findByUserid(stallCreate.getStallid());
        log.info(user.toString());
        Stall stall = stallRepository.findByStallid(user);


        log.info(stall.toString());

        if (stall==null){
            stall = Stall.builder()
                    .stallid(user)
                    .stallname(stallCreate.getStallname())
                    .stallexplanation(stallCreate.getStallexplanation())
                    .stallopen(Boolean.FALSE)
                    .latitude(stallCreate.getLatitude())
                    .longitude(stallCreate.getLongitude())
                    .build();
            stallRepository.save(stall);
        }
        return stall;
    }
}
