package com.THEmans.street_stall.Domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Stall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userid")
    private User user;

    private String stallname;
    private String stallexplanation;
    private Boolean stallopen;
    private Long Latitude; //위도
    private Long Longitude; //경도

    @Builder
    public Stall(User stallid, String stallname, String stallexplanation, Boolean stallopen, Long latitude, Long longitude){
        this.user=stallid;
        this.stallname=stallname;
        this.stallexplanation=stallexplanation;
        this.stallopen=stallopen;
        this.Latitude=latitude;
        this.Longitude=longitude;

    }

}
