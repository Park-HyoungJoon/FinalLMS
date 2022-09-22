package com.edo.user.service;


import com.edo.user.entity.Users;
import com.edo.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
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

//    회원가입 시 멤버 저장
    public Users saveMember(Users users) {
//        중복된 회원인지 먼저 확인
        validatDuplicateUsers(users);
//        중복되지 않았다면 회원가입
        return userRepository.save(users);
    }

//    가입된 회원에 한하여 예외 발생
    private void validatDuplicateUsers(Users users){
        Optional<Users> findUsers = userRepository.findByusersEmail(users.getUsersEmail());
        if(findUsers != null){
            throw  new IllegalStateException("이미 가입된 회원입니다.");
        }
    }


}
