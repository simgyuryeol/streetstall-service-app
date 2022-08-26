package com.THEmans.street_stall.Jwt;

import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Jwt.JwtProperties;
import com.THEmans.street_stall.Jwt.JwtService;
import com.THEmans.street_stall.Repository.UserRepository;
import com.THEmans.street_stall.Security.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;
    private JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override //SecurityConfig에서 antMatchers에 연결된 API 요청시 실행됨 토큰 검증
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ObjectMapper objectMapper  = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING); //요청들어온 accesstoke이라는 헤더의 값을 가져온다
        if(jwtHeader==null){
            /**
             * jwt 토큰이 없는 사용자 필터링
             */
            Map<String,String> jwtResponse = jwtService.requiredJwtTokenResponse();
            String result = objectMapper.writeValueAsString(jwtResponse);
            response.getWriter().write(result);

            return; //여기서 마무리 지어준다.
        }

        String token = request.getHeader(JwtProperties.HEADER_STRING);
        String userId = jwtService.validAccessToken(token);

        /**
         * 정상적인 access 토큰 사용자
         */
        if(userId!=null){
            User user = userRepository.findByUserid(userId);

            // 인증은 토큰 검증시 끝.
            // 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails, //나중에 컨트롤러에서 DI해서 쓸때 사용하기 편함
                    null, //패스워드는 모르니까 null 처리
                    principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장(권한체크를 스프링 시큐리티에게 위임하기 위해서
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request,response);
        }

        /**
         * access 토큰이 정상적이지 않거나 기간이 만료된 토큰일 경우
         * refresh토큰이 있을 경우 access 토큰을 생성시켜줌
         */
        else{

            Map<String,String> jwtResponse = jwtService.requiredRefreshTokenResponse();
            response.getWriter().write(objectMapper.writeValueAsString(jwtResponse));
            return;
        }
    }


}
