package com.edo.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edo.user.entity.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByMemberEmail(String usersEmail);

    boolean existsByMemberEmail(String usersEmail);


    //	닉네임 중복된 회원이 있는지 검사하기 위한 쿼리 메소드 작성
    Member findByMemberNickname(String usersNickname);
}
