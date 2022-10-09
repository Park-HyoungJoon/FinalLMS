package com.edo.user.controller;

import com.edo.user.dto.UserDto;
import com.edo.user.entity.Users;
import com.edo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    @Autowired
    private final UserService userService;

    @GetMapping(value="/login")
    public String Login(){
        return "member/login";
    }

    @PostMapping(value = "/login")
    public String LoginPost(){
        return null;
    }

//   이용약관
    @GetMapping(value="/memberjoin")
    public String MemberJoinGet(Model model){
        log.info("<<<>>>>>>>>>><<<<<<<<<<<<<<<<<<<>>>>>>>");
//        model.addAttribute("UserDto", new UserDto());
        return "member/memberjoin";
    }


// 이메일 등록
    @GetMapping(value="/memberjoinInfo")
    public String MemberjoinInfoGet(){
        return "member/memberjoinInfo";
    }

    //    회원가입 값 전달
    @PostMapping(value ="/memberjoinInfo" )
    public String MemberJoinPost( @Valid UserDto userDto, Model model){

//        로그 찍어보기
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>+++++++++++++++>>>>>>>>>>>>>>>>");

        Users users = new Users();
        users.setUsersEmail(userDto.getUsersEmail());
        users.setUsersNickname(userDto.getUsersNickname());
        users.setUsersName(userDto.getUsersName());
        users.setUsersPassword(userDto.getUsersPassword());
        users.setUsersPhone(userDto.getUsersPhone());

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>" + users.toString());

        userService.saveMember(userDto);

//        성공 시 로그인 페이지로 돌아간다
        return "redirect:/login";
    }

    // 마이페이지
    @GetMapping(value="/mypage")
    public String MypageGet(){
        return "mypage/mypageMain";
    }

    //로그인 실패 시 에러 메세지 나타냄
    @GetMapping(value = "/login/error")
    public String loginErrorGet(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 다시 확인해주세요");
        return "/login";
    }
}


