package com.edo.user.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edo.user.entity.Users;
import com.edo.user.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService
{

	// @Autowired
	private final UsersRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

	// 회원가입 시 멤버 저장
	public Long saveMember(Users users)
	{

		log.info(users.toString());

//        중복된 회원인지 먼저 확인

		validateDuplicateUserEmail(users.getUsersEmail());

//        중복되지 않았다면 회원가입
		Users savedUser = userRepository.save(users);
		log.info(savedUser.toString());
		return savedUser.getUsersId();
	}

	// 가입된 회원에 한하여 예외 발생
	public void validateDuplicateUserEmail(String userEmail)
	{
//        이메일 중복 확인
		Users findUsers = userRepository.findByusersEmail(userEmail).get();
		if (findUsers != null)
		{
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}

	}

	// 닉네임 중복 확인
	public void validateDuplicateNickname(String userNickname)
	{
		Users findUserNickname = userRepository.findByusersNickname(userNickname);
		if (findUserNickname != null)
		{
			throw new IllegalStateException("중복된 닉네임입니다. 다른 닉네임으로 설정해주세요");
		}
	}

//    로그인

//        @Override
//        public UserDetails loadUserByUsername (String usersEmail) throws UsernameNotFoundException {
//            Users users = userRepository.findByusersEmail(usersEmail);
//            log.info(">>>>>>>>>>>>>>>> 로그인이 되지 않고 있어요" + users);
//
//            if (users == null) {
//                log.info(">>>>>>>>>>>>>>>> 로그인이 되지 않고 있어요 IF문" + users); 
//                throw new UsernameNotFoundException(usersEmail);
//            }
////        이 User은 Spring security의 User
//            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 로그인 할 때 찍어보기" + users.toString());
//            return User.builder()
//                    .username(users.getUsersEmail()).password(users.getUsersPassword()).roles(users.getUsersRole().toString()).build();
//        }

	@Override
	public UserDetails loadUserByUsername(String usersEmail) throws UsernameNotFoundException
	{
		log.info("===========>" + usersEmail);
		Users user = userRepository.findByusersEmail(usersEmail).orElseThrow(()->new EntityNotFoundException("오류")); 

		log.info("===========>" + user.getUsersEmail() + ", " + user.getUsersRole().toString());
		return User.builder() // User 객체 생성하기
				.username(user.getUsersEmail()).password(user.getUsersPassword()).roles(user.getUsersRole().toString())
				.build();
	}
}
