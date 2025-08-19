package com.kangwon.fino.controller;


import com.kangwon.fino.user.dto.request.MemberRequest;
import com.kangwon.fino.user.service.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

    private final LoginServiceImpl loginService;



    @PostMapping("/join")
    public String joinProcess(@RequestBody MemberRequest.SignInDto SignInDto){

        loginService.joinProcess(SignInDto);

        return "ok";
    }
}
