package com.THEmans.street_stall.Service;

import com.THEmans.street_stall.Domain.Stall;
import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Dto.Stall_correction;
import com.THEmans.street_stall.Dto.Stall_create;
import com.THEmans.street_stall.Repository.StallRepository;
import com.THEmans.street_stall.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        User user=userRepository.findByUserid(stallCreate.getStallid()); //id값이 없는 user정보도 들어올까? 확실한 정보만 들어오는게 아닌가?
        Optional<Stall> findStall = stallRepository.findByUser_Userid(user.getUserid());

        Stall stall;
        if(findStall.isEmpty()) {
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
        else{
            stall=findStall.get();
        }

        return stall;
    }


    /**
     *  가게 정보 수정
     */
    public Stall StallCorrection(Long id,Stall_correction stall_correction){
        Stall stall=stallRepository.findById(id).get();

        stall.setStallname(stall_correction.getStallname());
        stall.setStallexplanation(stall_correction.getStallexplanation());
        stall.setLatitude(stall_correction.getLatitude());
        stall.setLongitude(stall_correction.getLongitude());

        stallRepository.save(stall);

        return stall;
    }

    /**
     * 가게 삭제
     */
    public User StallDelete(Long id){
        Stall stall = stallRepository.findById(id).get();
        User user = userRepository.findByUserid(stall.getUser().getUserid());
        stallRepository.delete(stall);
        return user;
    }

    /**
     * 가게 열기 닫기 설정
     */
    public Stall StallOpen(Long id, Map<String,Boolean> open){
        Boolean stallopen = open.get("stallopen");
        Stall stall = stallRepository.findById(id).get();
        stall.setStallopen(stallopen);
        stallRepository.save(stall);
        return stall;
    }


    /**
     * 가게 검색
     */
    public List<Stall> StallSearch(Map<String,String> stallname){
        List<Stall> stallList = stallRepository.findAllBystallnameLike("%"+stallname.get("stallname")+"%");
        return stallList;
    }

    /**
     * 가게 위치 정보
     */
    public List<Stall> StallLocation(){
        List<Stall> stallList = stallRepository.findAll();
        return stallList;
    }



}
