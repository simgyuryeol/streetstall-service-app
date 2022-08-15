package com.THEmans.street_stall.Dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class LoginDto {

    @NotNull
    private String loginId;

    @NotNull
    private String password;
}
