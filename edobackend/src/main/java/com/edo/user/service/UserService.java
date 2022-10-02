package com.edo.user.service;


import com.edo.user.dto.UserDto;
import com.edo.user.entity.Users;
import com.edo.user.repository.UsersRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UsersRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    //    회원가입 시 멤버 저장
    public Long saveMember(UserDto userDto) throws Exception {

        Users users = userDto.createUserDto();
//        중복된 회원인지 먼저 확인
        validateDuplicateUsers(users);

//        중복되지 않았다면 회원가입
         userRepository.save(users);
         return users.getUsersId();
    }


//    가입된 회원에 한하여 예외 발생
    private void validateDuplicateUsers(Users users){
//        이메일 중복 확인
        Optional<Users> findUsers = userRepository.findByusersEmail(users.getUsersEmail());
        if(findUsers != null){
            throw  new IllegalStateException("이미 가입된 회원입니다.");
        }
//        닉네임 중복 확인
        Optional<Users> findUserNickname = userRepository.findByusersNickname(users.getUsersNickname());
        if(findUserNickname != null){
            throw  new IllegalStateException("중복된 닉네임입니다. 다른 닉네임으로 설정해주세요");
        }
    }



}
