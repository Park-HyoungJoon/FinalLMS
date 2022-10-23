package com.edo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edo.user.entity.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
//	Optional<Users> findByusersEmail(String usersEmail);

    Users findByusersEmail(String usersEmail);

    boolean existsByusersEmail(String usersEmail);


    //	닉네임 중복된 회원이 있는지 검사하기 위한 쿼리 메소드 작성
    Users findByusersNickname(String usersNickname);
}
