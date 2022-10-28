package com.THEmans.street_stall.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@IdClass(MenuID.class)
public class Menu{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuid")
    private Stall menuid;

    @Id
    private String menuname;

    private int price;
    private String image;
    private String menuexplanation;
}
