package com.edo.community.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

//웹 페이지의 제한된 자원을 외부 도메인에서 접근을 허용
@CrossOrigin
@Controller
//final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequiredArgsConstructor
//로깅에 대한 추상체 제공
@Log4j2
public class CommunityController {

    @GetMapping(value = "communitymain")
    public String CommunityMain(){
        return "community/communityMain";
    }

    @GetMapping(value = "communitywrite")
    public String CommunityWrite(){
        return "community/communityWrite";
    }
}
