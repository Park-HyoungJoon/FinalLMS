package com.edo.lecture.repository;

import com.edo.lecture.entity.LectureDivide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Lec_ContentsRepository extends JpaRepository<LectureDivide,Integer> {
}
