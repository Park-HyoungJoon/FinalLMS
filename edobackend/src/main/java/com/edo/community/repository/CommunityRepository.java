package com.edo.community.repository;

import com.edo.community.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community,Long> {
    @Query(value = "select * from community order by community_id desc LIMIT ?1" , nativeQuery = true)
    List<Community> findDescCommunity(int limit_num);

    @Query(value = "select c.* from community c  where c.member_id = ?1 order by community_id desc LIMIT ?2", nativeQuery = true)
    List<Community> findAllById(Long id,int limit_num);

//    글 지우기
    @Modifying
    @Transactional
    @Query(value = "delete from community where community_id = ?1", nativeQuery = true)
    void deleteCommunitiesById(Long id);


    Page<Community> findAll(Pageable request);

//    파트 페이징
    Page<Community> findAllByCategory(Pageable request, String part);

}
