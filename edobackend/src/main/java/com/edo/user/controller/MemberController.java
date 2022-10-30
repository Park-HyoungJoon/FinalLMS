package com.edo.user.controller;

import javax.validation.Valid;

import com.edo.user.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.edo.user.entity.Member;
import com.edo.user.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;  



    @GetMapping(value="/login")
    public String Login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception, Model model) {
    	model.addAttribute("error", error);
    	model.addAttribute("exception", exception);    	
    	log.info("loginForm view resolve");

        return "member/login";
    }


//   이용약관
    @GetMapping(value="/join")
    public String MemberJoinGet(Model model){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 회원가입 들어가는 중 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        return "member/memberjoin";
    }


// 이메일 등록
    @GetMapping(value="/joinInfo")
    public String MemberjoinInfoGet(){
        return "member/memberjoinInfo";
    }

    //    회원가입 값 전달
    @PostMapping(value ="/joinInfo" )
    public String MemberJoinPost(@Valid MemberDto memberDto, Model model){


        Member member = Member.createUser(memberDto, passwordEncoder);
//        member.setUsersEmail(memberDto.getUsersEmail());
//        member.setUsersNickname(memberDto.getUsersNickname());
//        member.setUsersName(memberDto.getUsersName());
//        String password = passwordEncoder.encode(memberDto.getUsersPassword());
//        member.setUsersPassword(password);
//        member.setUsersPhone(memberDto.getUsersPhone());
//        member.setUsersRole(Role.USER);

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>" + member.toString());

        try{
            memberService.saveMember(member);
        } catch(IllegalStateException e){
            return "member/memberjoinInfo";
        }

//        성공 시 로그인 페이지로 돌아간다
        return "member/login";
    }

//    이메일 중복 확인을 위한 메소드
    @ResponseBody //view가 아닌 data 그대로를 반환합니다.
    @PostMapping(value = "/joinInfo/validateEmail")
    public Boolean ValidateEmail(@RequestBody MemberDto memberDto){
        log.info(memberDto.toString());
        try{
//        이메일 중복 검사 실행
            memberService.validateDuplicateMemberEmail(memberDto.getMemberEmail());

        }catch(IllegalStateException e){
            return false;
        }
        return true;
    }

    //    닉네임 중복 확인을 위한 메소드
    @ResponseBody
    @PostMapping(value = "/joinInfo/validateNickname")
    public Boolean ValidateNickname(@RequestBody MemberDto memberDto){
        log.info(memberDto.toString());
        try{
//        닉네임 중복 검사 실행
            memberService.validateDuplicateNickname(memberDto.getMemberNickname());
        }catch(IllegalStateException e){
            return false;
        }
        return true;
    }

    // 마이페이지
    @GetMapping(value="/mypage") 
    public String MypageGet(){
        return "mypage/mypageMain";
    }

    //로그인 실패 시 에러 메세지 나타냄 
    @GetMapping(value = "/login/error")
    public String loginErrorGet(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception, Model model){
    	log.info("=================> 오류 발생" + error + ", " + exception);
        model.addAttribute("loginErrorMsg", exception);
        return "/member/login";
    }
}


