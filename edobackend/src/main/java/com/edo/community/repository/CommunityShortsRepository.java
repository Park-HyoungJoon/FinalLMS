package com.edo.community.repository;

import com.edo.community.entity.CommunityShorts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityShortsRepository extends JpaRepository<CommunityShorts,Long> {

}
