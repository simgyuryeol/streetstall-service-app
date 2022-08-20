package com.THEmans.street_stall.Domain;

import lombok.Data;

@Data
public class KakaoProfile {


    public class Properties {
        public String nickname;
//        public String profile_image; //이미지 경로 필드1
//        public String thumbnail_image;
    }

    //public String id; //User의 userid에 들어가기위해서 String으로 선언

    public Properties properties;
    public KakaoAccount kakao_account; // 카카오 계정 정보
    public String id;
    public String connected_at; //서비스에 연결 완료된 시간
    @Data
    public class KakaoAccount {
        public Boolean profile_nickname_needs_agreement; // 사용자 동의시 닉네임 정보 제공 가능
        public Boolean has_email;
        public Boolean email_needs_agreement; //사용자 동의시 이메일 제공 가능
        public Boolean is_email_valid; // 이메일 유효 여부. true:유효 , false:이메일이 다른 카카오 계정에 사용돼 만료
        public Boolean is_email_verified; //이메일 인증 여부. true 인증된 이메일, false:인증되지 않은 이메일
        public String email; //카카오 계정 대표 이메일일
        public Profile profile;

        @Data
        public class Profile {
            public String nickname;
        }
//            public String thumbnail_image_url;
//            public String profile_image_url; //이미지 경로 필드2
//            public Boolean is_default_image;//
 //           }

        // public Boolean profile_image_needs_agreement; //사용자 동의시 프로필 사진 제공가능


        //public Boolean ci_needs_agreement; //사용자동의시 ci 참고 기능
        //public String ci; //연계정보

        //public String name; //카카오 계정 이름
        //public Boolean name_needs_agreement; //사용자 동의시 카카오 계정 이름 제공 가능



    }
}
