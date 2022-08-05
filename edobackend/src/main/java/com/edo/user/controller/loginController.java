package com.edo.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edo.user.dto.UserDto;
import com.edo.user.entity.User;
import com.edo.user.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class loginController {

	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private final UserService userService;
	
	@GetMapping(value="/")
	public ResponseEntity<?> Home(){
		return new ResponseEntity<>("localhost:3000으로 가보쇼",HttpStatus.OK);
	}
//	로그인
	@PostMapping(value="/login")
	public ResponseEntity<?> loginUser(@RequestBody UserDto userDto){
		System.out.println(userDto.getEmail());
		Long num = userService.findUser(userDto);
		System.out.println(num);
//		0이 나오면 
		if(num==0) {
			return new ResponseEntity<>( userService.findUser(userDto),HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<>( userService.findUser(userDto),HttpStatus.OK);
		}
	}
//	회원가입
	@PostMapping(value="/user")
	public ResponseEntity<?> newUser(@RequestBody UserDto userDto,BindingResult bindingResult) {
		try {
			User user = User.createUser(userDto, passwordEncoder);
			userService.save(user);
		}catch (IllegalStateException e) {
			return new ResponseEntity<>("실패하였습니다.",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("성공하였습니다.",HttpStatus.OK);
	}
}
