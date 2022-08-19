package com.THEmans.street_stall.Jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refreshToken_id")
    private Long id;

    @Column(name = "refreshToken",length = 500)
    private String refreshToken;

    public RefreshToken(String refreshToken){ //사용자들의 refresh 토큰을 따로 관리하기 위해 별도의 DB를 구성 1대1로 매핑
        this.refreshToken = refreshToken;
    }
}
