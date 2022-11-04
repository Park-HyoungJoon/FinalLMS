package com.edo.community.controller;


import com.edo.community.dto.CommunityDto;
import com.edo.community.entity.Community;
import com.edo.community.entity.CommunityTest;
import com.edo.community.repository.CommunityRepository;
import com.edo.community.repository.CommunityTestRepository;
import com.edo.community.service.CommunityService;
import com.edo.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

//웹 페이지의 제한된 자원을 외부 도메인에서 접근을 허용
@CrossOrigin
@Controller
//final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequiredArgsConstructor
//로깅에 대한 추상체 제공
@Slf4j
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityTestRepository communityTestRepository;
    private final CommunityRepository communityRepository;

    private final MemberService memberService;



    @GetMapping(value = "test")
    public String test() {
        return "putoff/test";
    }

    @GetMapping(value = "/main")
    public String CommunityMain(CommunityDto communityDto, Model model, Principal principal){
        log.info(communityDto.toString());

        if(principal == null){
            model.addAttribute("message", "사용자 없음");
        }else{
            model.addAttribute("message", principal.getName());
            log.info(">>>>>>>>>>>>>> 사용자를 읽어오고 있어요 <<<<<<<<<<<<<<<<<<< " + principal.getName().toString());
            log.info(">>>>>>>>>>>>>> 이것도 가능하려나 " + memberService.communityNickname(principal.getName()) +" <<<<<<<<<<<<<<<<<<< ");

    }

    CommunityTest communityTest = new CommunityTest();
//        communityTest.setCommunityTitle(communityDto.getCommunityTitle());
//        communityTest.setCommunityContent(communityDto.getCommunityContents());
//        log.info(communityTest.toString());

        List<CommunityDto> communityMainList = communityService.getCommunityList(communityTest);
        model.addAttribute("communityMainList",communityMainList);

        return "community/communityMain";
    }


    @GetMapping(value = "/write")
    public String CommunityWrite(Model model){
        model.addAttribute("communityDto", new CommunityDto());
        return "community/communityWrite";
    }

//    값 전달 테스트
    @PostMapping(value = "/write")
    public String CommunityWrite(@Valid CommunityDto communityDto, Model model){
        log.info(communityDto.toString());

        Community community = communityService.createRealContents(communityDto);
        log.info(community.toString());
        communityRepository.save(community);

//        List<CommunityDto> communityMainList = communityService.getCommunityList(community);
//        model.addAttribute("communityMainList",communityMainList);


//          성공 시 메인 페이지로 돌아감
        return "redirect:/community/main";
    }

    @GetMapping(value = "/write/1")
    public String communityWrite(Model model){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        CommunityTest communityTest = communityTestRepository.getById(1L);
        log.info(communityTest.toString());

        model.addAttribute("communityTest",communityTest);
        return "community/communityDetail";
    }

//    게시글 상세 조회
    @GetMapping(value = "/write/{communityId}")
    public String communityDetail(@PathVariable("communityId") Long communityId, Model model){
        return null;
    }

    //쇼츠 조회
    @GetMapping(value = "/shorts")
    public String communityShorts(Model model){
        int heart = 0;
        model.addAttribute("heart",heart);

        return "community/communityShorts";
    }

}
