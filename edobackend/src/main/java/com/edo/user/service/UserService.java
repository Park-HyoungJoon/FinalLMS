package com.edo.user.service;


import com.edo.user.dto.UserDto;
import com.edo.user.entity.Users;
import com.edo.user.repository.UsersRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    //    회원가입 시 멤버 저장
    public Long saveMember(UserDto userDto) {

        Users users = userDto.createUsers();
        log.info(users.toString());
//        중복된 회원인지 먼저 확인
        try{
            validateDuplicateUsers(users);
        } catch(Exception e){
            log.error(e.getMessage());
        }

//        중복되지 않았다면 회원가입
         Users savedUser = userRepository.save(users);
         log.info(savedUser.toString());
         return savedUser.getUsersId();
    }


//    가입된 회원에 한하여 예외 발생
    private void validateDuplicateUsers(Users users){
//        이메일 중복 확인
        Users findUsers = userRepository.findByusersEmail(users.getUsersEmail());
        if(findUsers != null){
            throw  new IllegalStateException("이미 가입된 회원입니다.");
        }

//        닉네임 중복 확인
        Optional<Users> findUserNickname = userRepository.findByusersNickname(users.getUsersNickname());
        if(findUserNickname != null){
            throw  new IllegalStateException("중복된 닉네임입니다. 다른 닉네임으로 설정해주세요");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String usersEmail) throws UsernameNotFoundException {
        Users users = userRepository.findByusersEmail(usersEmail);
        if(users == null){
            throw new UsernameNotFoundException(usersEmail);
        }
//        이 User은 Spring security의 User
//        회원가입에 아직 roll 기능을 만들지 않아서 보류. 추후 기능 구현 시 추가할 예정
        return User.builder()
                .username(users.getUsersEmail())
                .password(users.getUsersPassword())
                .build();
    }



}
