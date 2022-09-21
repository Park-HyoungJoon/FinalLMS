package com.edo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping(value="/login")
    public String Login(){
        return "member/login";
    }

//   이용약관
    @GetMapping(value="/memberjoin")
    public String MemberJoin(){
        return "member/memberjoin";
    }

// 이메일 등록
    @GetMapping(value="/memberjoinEmail")
    public String MemberjoinEmail(){
        return "member/memberjoinEmail";
    }

    // 이메일 등록
    @GetMapping(value="/mypage")
    public String Mypage(){
        return "mypage/mypageMain";
    }
}
