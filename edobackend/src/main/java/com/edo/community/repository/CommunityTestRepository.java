package com.edo.community.repository;

import com.edo.community.entity.CommunityTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityTestRepository extends JpaRepository<CommunityTest,Long> {

    @Query(value = "select * from community_test order by community_id desc LIMIT ?1" , nativeQuery = true)
    List<CommunityTest> findDescCommunity(int limit_num);
//    id 오름차순 조회
//    id로 조회할 예정이니까 매개변수가 아닌 쿼리메소드에 id를 넣는다
    List<CommunityTest> findAllByOrderByCommunityId();

//    id만 오름차순으로 조회
//    List<CommunityTest> findByCommunityIdOrderByCommunityId();


}
