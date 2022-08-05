package com.edo.user.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edo.user.dto.UserDto;
import com.edo.user.entity.User;
import com.edo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService  {

	@Autowired
	private UserRepository userRepository;
	public User save(User user) {
		validateDuplicateUser(user);
		return userRepository.save(user);
	}
	
	private void validateDuplicateUser(User user) {
		User findUser = userRepository.findByEmail(user.getEmail());
		if(findUser !=null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}

	public Long findUser(UserDto userDto) {
		try {
		User user = userRepository.findByEmail(userDto.getEmail());
			return user.getId();
		}
		catch (Exception e) {
			return  (long) 0;
		}
	}

}
