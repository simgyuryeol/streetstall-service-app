package com.THEmans.street_stall.Dto;

import lombok.Data;

@Data
public class Stall_create {
    private String stallid;
    private String stallname;
    private String stallexplanation;
   // private Boolean stallopen;
    private Long Latitude; //위도
    private Long Longitude; //경도
}
