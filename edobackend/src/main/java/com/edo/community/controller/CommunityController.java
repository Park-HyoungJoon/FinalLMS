package com.edo.community.controller;

import com.edo.community.dto.CommunityDto;
import com.edo.community.dto.CommunityShortsFileDto;
import com.edo.community.entity.Community;
import com.edo.community.entity.CommunityShorts;
import com.edo.community.repository.CommunityRepository;
import com.edo.community.repository.CommunityShortsRepository;
import com.edo.community.service.CommunityService;
import com.edo.user.entity.Member;
import com.edo.user.service.MemberService;
import com.edo.util.file.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private final CommunityRepository communityRepository;
    private final MemberService memberService;

    private final CommunityShortsRepository communityShortsRepository;
    String videoPath;
    @Value("${shorts.path}")
    private String fileRoot;


    @GetMapping(value = "/main")
    public String CommunityMain( @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "9 ") int size,
                                 Model model) {

        List<Community> communityList = communityService.getMainList();
        log.info("리스트를 잘 가져오고 있나요 >>>>>>>>>>>>>>>>>>>>>>>>>>> " + communityList.get(0).toString() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        model.addAttribute("posts", communityService.getPage(pageNumber, size));
        return "community/communityMain";
    }



    @GetMapping(value = "/community/{part}")
    public String communityPart
            (@PathVariable(value = "part", required = false) String part,
             @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
             @RequestParam(value = "size", required = false, defaultValue = "9") int size,
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

        Member member = memberService.communityMember(principal.getName());
        log.info("사용자정보" + member);
        model.addAttribute("member", member);
        return "community/communityWrite";
    }

    //    값 전달 테스트
    @PostMapping(value = "/write")
    public String CommunityWrite(@Valid CommunityDto communityDto, Model model) {


        Community community = communityService.createRealContents(communityDto);
        log.info("CommunityDto 찍기" + communityDto.toString());
        communityRepository.save(community);


//          성공 시 메인 페이지로 돌아감
        return "redirect:/community/main";
    }


    //    게시글 상세 조회
    @GetMapping(value = "/detail/{id}")
    public String communityDetail(@PathVariable(value = "id") Long id, Model model) {
        log.info("커뮤니티 아이디를 가져오나요?" + id);
            Community communityDetail = communityService.updateHit(id);

            model.addAttribute("communityDetail",communityDetail);
        return "community/communityDetail";
}

    @GetMapping(value = "/update/{id}")
    public String communityUpdate(@PathVariable(value = "id") Long id, Model model) {
        log.info("커뮤니티 아이디를 가져오나요?" + id);
        Community communityDetail = communityService.updateHit(id);

        model.addAttribute("communityDetail",communityDetail);
        return "community/communityUpdate";
    }

    //쇼츠 조회
    @GetMapping(value = "/shorts")
    public String communityShorts(Model model) {
        List<CommunityShorts> communityShortsList = communityShortsRepository.findAll();
        model.addAttribute("shortsList",communityShortsList);
        int heart = 0;
        model.addAttribute("heart", heart);

        return "community/communityShorts";
    }
    //쇼츠 업로드 페이지 이동
    @GetMapping(value = "/shorts/upload")
    public String shortsUpload(Model model){
        return "community/communityShortsUpload";
    }
    //쇼츠 파일 업로드
    @PostMapping(value = "/shorts/uploader", consumes = "multipart/form-data")
    public String ContentsFileCreate(FileVO fileVO,Principal principal, Model model)
            throws IOException {
            videoPath = fileRoot;

        MultipartFile multipartFile = fileVO.getShorts();
        /*파일 videoPath에 저장*/
        String uuid = UUID.randomUUID().toString();
        String uuidFileName = uuid + "_" + multipartFile.getOriginalFilename();

        File file = new File(videoPath,uuidFileName);

        multipartFile.transferTo(file);

        //파일정보 communityShorts에 저장
        String memberEmail = principal.getName();
        Member member = memberService.communityMember(memberEmail);
        CommunityShortsFileDto communityShortsFileDto = new CommunityShortsFileDto();
        CommunityShorts communityShorts = communityShortsFileDto.toCommunityShorts(multipartFile,member,uuidFileName);
        communityService.shortsSave(communityShorts);
        List<CommunityShorts> communityShortsList = communityShortsRepository.findAll();
        model.addAttribute("shortsList",communityShortsList);
        int heart = 0;
        model.addAttribute("heart", heart);

        return "community/communityShorts";
    }


}
