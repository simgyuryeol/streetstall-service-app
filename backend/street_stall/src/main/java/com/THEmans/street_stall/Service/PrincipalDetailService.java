package com.THEmans.street_stall.Service;

import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Repository.UserRepository;
import com.THEmans.street_stall.Security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {
    // http://localhost:8080 /login 호출시 (스프링 시큐리티 자동 uri) -> 동작하지 않는다.
    // formlogin사용 안하니 -> SpringSecurityFilter를 해서 해결결}

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User findUser = userRepository.findByUserid(username);
        return new PrincipalDetails(findUser);
    }
}