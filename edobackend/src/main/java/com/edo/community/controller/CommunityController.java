package com.edo.community.controller;


import com.edo.community.dto.CommunityTestDto;
import com.edo.community.entity.Community;
import com.edo.community.entity.CommunityTest;
import com.edo.community.repository.CommunityRepository;
import com.edo.community.repository.CommunityTestRepository;
import com.edo.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

//웹 페이지의 제한된 자원을 외부 도메인에서 접근을 허용
@CrossOrigin
@Controller
//final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequiredArgsConstructor
//로깅에 대한 추상체 제공
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityTestRepository communityTestRepository;


    @GetMapping(value = "test")
    public String test() {
        return "putoff/test";
    }
    @GetMapping(value = "communitymain")
    public String CommunityMain(){
        return "community/communityMain";
    }

    @GetMapping(value = "communitywrite")
    public String CommunityWrite(Model model){
        model.addAttribute("communityTestDto", new CommunityTestDto());
        return "community/communityWrite";
    }

//    값 전달 테스트
    @PostMapping(value = "communitywrite")
    public String communityWriteTest(@Valid CommunityTestDto communityTestDto, Model model){
        log.info(communityTestDto.toString());
//
//        if(bindingResult.hasErrors()){
////            에러 시 글 작성 페이지로 다시 돌아감
//            return "community/communityWrite";
//        }

        CommunityTest communityTest = new CommunityTest();
        communityTest.setCommunityTitle(communityTestDto.getCommunityTitle());
        communityTest.setCommunityContent(communityTestDto.getCommunityContents());
        log.info(communityTest.toString());
        communityTestRepository.save(communityTest);

        List<CommunityTestDto> communityMainList = communityService.getCommunityList(communityTest);
        model.addAttribute("communityMainList",communityMainList);


//          성공 시 메인 페이지로 돌아감
        return "community/communityMain";
    }

    @GetMapping(value = "/communitywrite/1")
    public String communityWrite(Model model){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        CommunityTest communityTest = communityTestRepository.getById(1L);
        log.info(communityTest.toString());

        model.addAttribute("communityTest",communityTest);
        return "community/communityWriteDetail";
    }


}
