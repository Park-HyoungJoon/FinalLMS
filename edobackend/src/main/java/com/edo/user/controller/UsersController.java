package com.edo.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edo.user.constant.Role;
import com.edo.user.dto.UserDto;
import com.edo.user.entity.Users;
import com.edo.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    @Autowired
    private final UserService userService;

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
    @GetMapping(value="/memberjoin")
    public String MemberJoinGet(Model model){
        log.info("<<<>>>>>>>>>><<<<<<<<<<<<<<<<<<<>>>>>>>");
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


        Users users = Users.createUser(userDto, passwordEncoder);
//        users.setUsersEmail(userDto.getUsersEmail());
//        users.setUsersNickname(userDto.getUsersNickname());
//        users.setUsersName(userDto.getUsersName());
//        String password = passwordEncoder.encode(userDto.getUsersPassword());
//        users.setUsersPassword(password);
//        users.setUsersPhone(userDto.getUsersPhone());
//        users.setUsersRole(Role.USER);

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>" + users.toString());

        try{
            userService.saveMember(users);
        } catch(IllegalStateException e){
            return "member/memberjoinInfo";
        }

//        성공 시 로그인 페이지로 돌아간다
        return "member/login";
    }

//    이메일 중복 확인을 위한 메소드
    @ResponseBody //view가 아닌 data 그대로를 반환합니다.
    @PostMapping(value = "/memberjoinInfo/validateEmail")
    public Boolean ValidateEmail(@RequestBody UserDto userDto){
        log.info(userDto.toString());
        try{
//        이메일 중복 검사 실행
            userService.validateDuplicateUserEmail(userDto.getUsersEmail());

        }catch(IllegalStateException e){
            return false;
        }
        return true;
    }

    //    닉네임 중복 확인을 위한 메소드
    @ResponseBody
    @PostMapping(value = "/memberjoinInfo/validateNickname")
    public Boolean ValidateNickname(@RequestBody UserDto userDto){
        log.info(userDto.toString());
        try{
//        닉네임 중복 검사 실행
            userService.validateDuplicateNickname(userDto.getUsersNickname());
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


