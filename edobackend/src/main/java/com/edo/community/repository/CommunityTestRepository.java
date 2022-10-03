package com.edo.community.repository;

import com.edo.community.entity.CommunityTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityTestRepository extends JpaRepository<CommunityTest,Long> {

//    id 오름차순 조회
//    id로 조회할 예정이니까 매개변수가 아닌 쿼리메소드에 id를 넣는다
    List<CommunityTest> findAllByOrderByCommunityId();


}
