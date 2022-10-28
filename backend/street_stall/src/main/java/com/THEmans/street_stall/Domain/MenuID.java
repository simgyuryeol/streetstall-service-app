package com.THEmans.street_stall.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuID implements Serializable {
    private Stall menuid;
    private String menuname;
}
