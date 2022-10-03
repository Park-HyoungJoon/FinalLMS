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

//   이용약관
    @GetMapping(value="/memberjoin")
    public String MemberJoin(Model model){
        model.addAttribute("UserDto", new UserDto());
        return "member/memberjoin";
    }


// 이메일 등록
    @GetMapping(value="/memberjoinInfo")
    public String MemberjoinInfo(){
        return "member/memberjoinInfo";
    }

    //    회원가입 값 전달
    @PostMapping(value ="/memberjoinInfo" )
    public String userJoin(@Valid UserDto userDto, Model model){

//        로그 찍어보기
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>" + userDto.toString());

        Users users = new Users();
        users.setUsersEmail(userDto.getUsersEmail());
        users.setUsersNickname(userDto.getUsersNickname());
        users.setUsersName(userDto.getUsersName());
        users.setUsersPassword(userDto.getUsersPassword());
        users.setUsersPhone(userDto.getUsersPhone());

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>" + users.toString());

        userService.saveMember(userDto);

//        성공 시 메인 페이지로 돌아간다
        return "redirect:/";
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


