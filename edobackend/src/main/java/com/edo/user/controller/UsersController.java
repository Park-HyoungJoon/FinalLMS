package com.edo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class UsersController {

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
    @GetMapping(value="/memberjoinInfo")
    public String MemberjoinInfo(){
        return "member/memberjoinInfo";
    }

    // 마이페이지
    @GetMapping(value="/mypage")
    public String Mypage(){
        return "mypage/mypageMain";
    }

    //로그인 실패 시 에러 메세지 나타냄
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 다시 확인해주세요");
        return "/login";
    }
}


