package com.edo.lecture.repository;

import com.edo.lecture.entity.LectureUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Lecture_UserRepository extends JpaRepository<LectureUsers,Integer> {
}