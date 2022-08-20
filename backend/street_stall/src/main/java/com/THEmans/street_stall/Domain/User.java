package com.THEmans.street_stall.Domain;

import com.THEmans.street_stall.Jwt.RefreshToken;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id; //인덱스

    private String userid; //카카오 고유 id, 연계정보
    private String name; //유저 이름
    private String email;
    //private String nickname;

    private String roles; // 사용자, 판매자, 관리자

    private String password;
//    private String provider;
//    private String profileImg;
//    private LocalDateTime createTime;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "refreshToken")
    private RefreshToken jwtRefreshToken;

    @Builder
    public User(String userid, String name, String roles, String email) {
        this.userid = userid;
        this.name = name;
        this.roles = roles;
        this.email = email;

    }

    /**
     *  refresh 생성자, setter
     */
    public void createRefreshToken(RefreshToken refreshToken){
        this.jwtRefreshToken = refreshToken;
    }
    public void SetRefreshToken(String refreshToken){
        this.jwtRefreshToken.setRefreshToken(refreshToken);
    }

    /**
     * 사용자가 다양한 권한을 가지고 있을 수 있음
     */
    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }



}
