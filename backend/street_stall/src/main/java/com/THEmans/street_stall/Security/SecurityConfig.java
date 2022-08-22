package com.THEmans.street_stall.Security;

import com.THEmans.street_stall.Jwt.JwtAuthenticationFilter;
import com.THEmans.street_stall.Jwt.JwtAuthorizationFilter;
import com.THEmans.street_stall.Jwt.JwtService;
import com.THEmans.street_stall.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity //시큐리티 활성화 -> 기본 스프링 필터 체인에 등록
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorConfig config;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .antMatchers("/join","/","/home","/login/kakao","/login/oauth2/code/kakao","/refresh/**","/api/logout/**");
        //로그인할 때 허용하는 범위 작성. 권한 에러 뜨면 여기를 수정해야함
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않겠다, login시 세션을 검증하는 필터를 사용하지 않겠다.
                .and()
                .formLogin().disable() //formLogin 방식 사용 안함, json방식으로 전달
                .httpBasic().disable() //Bearer 방식 사용 -> header에 authentication에 토큰을 넣어 전달하는 방식

                .apply(new MyCustomDsl())
                .and()
//해당 api로 들어갈시 access토큰 자동 검증. 만료됬을 시 에러 응답메시지 보냄
                .authorizeRequests()
                .antMatchers("/api/v1/user/**").hasAuthority("USER")
                .antMatchers("/api/v1/manager/**").hasAuthority("MANAGER")
                .antMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                //.antMatchers("/login").hasAnyAuthority()
                .anyRequest().permitAll() //다른 요청은. 어떤 사용자든 접근 가능 이라는 뜻

                .and()
                .build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            http//.cors().configurationSource(config.corsFilter())
//                    .and()
                    .addFilter(config.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager,jwtService)) //AuthenticationManager가 있어야 된다 파라미터로
                    .addFilter(new JwtAuthorizationFilter(authenticationManager,userRepository,jwtService));
        }
    }

    @Bean //BCryptPasswordEncoder 에러로 인해 Bean 추가
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }





}
