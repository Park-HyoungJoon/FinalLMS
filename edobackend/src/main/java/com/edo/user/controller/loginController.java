package com.edo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class loginController {

    @GetMapping(value="/login")
    public String Login(){
        return "member/login";
    }

    @GetMapping(value="/memberjoin")
    public String MemberJoin(){
        return "member/memberjoin";
    }
}
