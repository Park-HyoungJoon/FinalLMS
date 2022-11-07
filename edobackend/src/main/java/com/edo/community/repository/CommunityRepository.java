package com.edo.community.repository;

import com.edo.community.entity.Community;
import com.edo.community.entity.CommunityTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community,Integer> {
    @Query(value = "select * from community order by community_id desc LIMIT ?1" , nativeQuery = true)
    List<Community> findDescCommunity(int limit_num);

//    @Query(value = "select c from Community c inner join Member m on c.id = m.memberId where c.id = :id")
    @Query(value = "select * from community c , member m where c.member_id = m.member_id and c.member_id = :id", nativeQuery = true)
    List<Community> findAllById(@Param("id") Long communityId);

}
