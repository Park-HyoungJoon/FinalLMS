package com.edo.lecture.repository;

import com.edo.lecture.entity.Lec_Divide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Lec_ContentsRepository extends JpaRepository<Lec_Divide,Integer> {
}
