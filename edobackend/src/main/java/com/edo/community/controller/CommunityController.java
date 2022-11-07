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
    public String CommunityMain(Model model) {

        List<Community> communityList = communityService.getMainList();
        model.addAttribute("communityList", communityList);
        log.info("리스트를 잘 가져오고 있나요 >>>>>>>>>>>>>>>>>>>>>>>>>>> " + communityList.get(0).toString() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        return "community/communityMain";
    }



    @GetMapping(value = "/community/{part}")
    public String communityPart
            (@PathVariable(value = "part", required = false) String part,
             @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
             @RequestParam(value = "size", required = false, defaultValue = "5") int size,
             Model model) {

        List<Community> communityList = communityRepository.findAll();
        model.addAttribute("posts", communityService.getPage(pageNumber, size));
        model.addAttribute("partPage", communityService.getPageByPart(pageNumber, size,part));
        model.addAttribute("communityList",communityList);
        model.addAttribute("part",part);

        return "community/communityMain";

    }

    @GetMapping(value = "/write")
    public String getCommunityWrite(Principal principal, Model model) {
        log.info("닉네임 가져오는지" + principal.getName() + "<<<<<<<<<<<<<<<<<<<<<");
       String nickname = memberService.communityNickname(principal.getName());

        model.addAttribute("nickname", nickname);
//        log.info("하 일단 출력이나 해보자 >>>>>>>>>." + new CommunityDto().toString() + "<<<<<<<<<<<<<<<<<<<<<<");
        return "community/communityWrite";
    }

    //    값 전달 테스트
    @PostMapping(value = "/write")
    public String CommunityWrite(@Valid CommunityDto communityDto, Model model) {



        Community community = communityService.createRealContents(communityDto);
        log.info(community.toString());
        communityRepository.save(community);

//        List<CommunityDto> communityMainList = communityService.getCommunityList(community);
//        model.addAttribute("communityMainList",communityMainList);


//          성공 시 메인 페이지로 돌아감
        return "redirect:/community/main";
    }

    @GetMapping(value = "/write/1")
    public String communityWrite(Model model) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        CommunityTest communityTest = communityTestRepository.getById(1L);
        log.info(communityTest.toString());

        model.addAttribute("communityTest", communityTest);
        return "community/communityDetail";
    }

    //    게시글 상세 조회
    @GetMapping(value = "/detail/{id}")
    public String communityDetail(@PathVariable(value = "id") Long id, Model model) {
            List<Community> communityDetail = communityService.getContent(id);
            log.info(">>>>>>>>>>>>>>>>>>>> 값을 잘 가져오나요? " + communityDetail.get(0).toString() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            model.addAttribute("communityDetail",communityDetail);
        return "community/communityDetail";
    }

    //쇼츠 조회
    @GetMapping(value = "/shorts")
    public String communityShorts(Model model) {
        int heart = 0;
        model.addAttribute("heart", heart);

        return "community/communityShorts";
    }

}
