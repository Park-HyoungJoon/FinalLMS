//package com.edo.user.controller;
//
//import javax.validation.Valid;
//
//import com.edo.config.dto.TokenDto;
//import com.edo.user.dto.UserRequestDto;
//import com.edo.user.dto.UserResponseDto;
//import com.edo.user.service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.edo.user.dto.UserDto;
//import com.edo.user.entity.User;
//import com.edo.user.service.UserService;
//
//import lombok.RequiredArgsConstructor;
//
//@CrossOrigin
//@Controller
//@RequiredArgsConstructor
//public class loginController {
//	@Autowired
//	private final UserService userService;
//	@Autowired
//	private AuthService authService;
//	@GetMapping(value="/")
//	public ResponseEntity<?> Home(){
//		return new ResponseEntity<>("localhost:3000으로 가보쇼",HttpStatus.OK);
//	}
//	@GetMapping("/me")
//	public ResponseEntity<UserResponseDto> getMyUserInfo(){
//		UserResponseDto myInfoBySecurity = userService.getMyInfoBySecurity();
//		return ResponseEntity.ok(myInfoBySecurity);
//	}
//
//	//	로그인
//	@PostMapping(value="/auth/login")
//	public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto requestDto){
//		return new ResponseEntity<>(authService.login(requestDto),HttpStatus.OK);
//		//return ResponseEntity.ok(authService.login(requestDto));
//	}
////	회원가입
//	@PostMapping(value="/auth/signup")
//	public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto requestDto) {
//		return ResponseEntity.ok(authService.signup(requestDto));
//	}
//}
