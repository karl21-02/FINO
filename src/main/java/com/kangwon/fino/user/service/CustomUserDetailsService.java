package com.kangwon.fino.user.service;


import com.kangwon.fino.global.domain.TblUser;
import com.kangwon.fino.user.dto.CustomUserDetails;
import com.kangwon.fino.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    // implements UserDetailsService 오류해결용 오버라이딩 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TblUser userData = userRepository.findByUsername(username);

        if (userData != null) {

            return new CustomUserDetails(userData);
        }

        return null;
    }
}
