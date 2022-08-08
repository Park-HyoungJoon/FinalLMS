package com.edo.user.service;


import com.edo.config.SecurityUtil;
import com.edo.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edo.user.dto.UserDto;
import com.edo.user.entity.User;
import com.edo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class UserService  {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;



	public UserResponseDto getMyInfoBySecurity(){
		return userRepository.findById(SecurityUtil.getCurrentUserId())
				.map(UserResponseDto::of)
				.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	}
//	public User save(User user) {
//		validateDuplicateUser(user);
//		return userRepository.save(user);
//	}
//
//	private void validateDuplicateUser(User user) {
//		User findUser = userRepository.findByEmail(user.getEmail());
//		if(findUser !=null) {
//			throw new IllegalStateException("이미 가입된 회원입니다.");
//		}
//	}
//
//	public Long findUser(UserDto userDto) {
//		try {
//		User user = userRepository.findByEmail(userDto.getEmail());
//			return user.getId();
//		}
//		catch (Exception e) {
//			return  (long) 0;
//		}
//	}

}
