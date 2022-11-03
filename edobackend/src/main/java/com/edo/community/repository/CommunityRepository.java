package com.edo.community.repository;

import com.edo.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community,Integer> {

}
