package com.kangwon.fino.user.service;

import com.kangwon.fino.domain.TblUser;
import com.kangwon.fino.user.domain.RoleType;
import com.kangwon.fino.user.dto.request.MemberRequest;
import com.kangwon.fino.user.dto.response.MemberResponse;
import com.kangwon.fino.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    // @RequiredArgsConstructor 생성자 생성 여부 결정
    /*
    public LoginServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    */

    public void joinProcess(MemberRequest.SignInDto signInDto) {

        String password = signInDto.getPassword();
        String username = signInDto.getUsername();

        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist){

            return;
        }

        TblUser data = new TblUser();

        data.setPassword(bcryptPasswordEncoder.encode(password));
        data.setUsername(username);
        data.setRole("Role_ADMIN");

        userRepository.save(data);


    }
}
