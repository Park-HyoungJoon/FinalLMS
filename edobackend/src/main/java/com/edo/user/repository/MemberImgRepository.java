package com.edo.user.repository;

import com.edo.user.entity.MemberImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberImgRepository extends JpaRepository<MemberImage,Long> {

//    List<MemberImage> findByMemberIdOrderByIdAsc(Long );
}
