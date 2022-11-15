package com.THEmans.street_stall.Dto;

import lombok.Data;

@Data
public class Stall_correction {
   // private String stallid;
    private String stallname;
    private String stallexplanation;
    private Long Latitude; //위도
    private Long Longitude; //경도
}
