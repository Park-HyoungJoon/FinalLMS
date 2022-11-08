package com.edo.user.repository;

import java.util.List;
import java.util.Optional;

import com.edo.community.entity.CommunityTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edo.user.entity.Member;

import javax.persistence.EntityManager;


@Repository
public interface MemberRepository extends JpaRepository<Member, String> {


    Optional<Member> findByMemberEmail(String usersEmail);

    boolean existsByMemberEmail(String usersEmail);


    //	닉네임 중복된 회원이 있는지 검사하기 위한 쿼리 메소드 작성
    Member findByMemberNickname(String usersNickname);

    List<Member> findAllByOrderByMemberId();
    
//    게시글 작성할 때 닉네임 찾는 쿼리메소드
//public Optional<User> findByLoginId(String loginId) {


    public Optional<Member> findByMemberId(Long memberId);

    //유저 이메일을 갖고 닉네임 찾기
    @Query(value = "select member_nickname from member where member_email=?1", nativeQuery = true)
    String findMemberNicknameByMemberEmail(String email);
}
