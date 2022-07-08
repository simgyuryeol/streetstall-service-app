package com.THEmans.street_stall.Domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {

    @Id @Column(nullable = false, name = "id", unique = true)
    private String id;


    private String nickname;
    private String password;
    private String user_state;
    private Boolean Guest;
    private Boolean Stall;
    private Boolean Manager;
}
