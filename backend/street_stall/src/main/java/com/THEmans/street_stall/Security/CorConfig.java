package com.THEmans.street_stall.Security;

//import org.apache.catalina.filters.CorsFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


//JWT를 사용할 때 반드시 해야됨
@Configuration
public class CorConfig{ //다른 출처의 자원을 사용할 수 있게 해주는 정책
    @Bean //CorsConfigurationSource
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); //내 서버가 응답 할때 json을 자바스크립트에서 처리할수 있게 할지
        config.addAllowedOrigin("*"); //모든 아이피를 응답 허용
        config.addAllowedHeader("*"); //모든 header 응답 허용
        config.addExposedHeader("*");
        config.addAllowedMethod("*"); //모든 post,get,put 허용

        source.registerCorsConfiguration("/api/**",config);

        return new CorsFilter(source);

    }
}
