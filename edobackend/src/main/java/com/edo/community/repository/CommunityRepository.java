package com.edo.community.repository;

import com.edo.community.entity.Community;
import com.edo.community.entity.CommunityTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community,Integer> {
    @Query(value = "select * from community order by community_id desc LIMIT ?1" , nativeQuery = true)
    List<Community> findDescCommunity(int limit_num);
}
