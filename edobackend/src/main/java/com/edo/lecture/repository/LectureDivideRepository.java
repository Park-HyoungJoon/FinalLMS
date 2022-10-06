package com.edo.lecture.repository;

import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureDivide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureDivideRepository extends JpaRepository<LectureDivide,Long> {
    @Query(value = "SELECT MAX(lecture_divide_seq) from lecture_divide" ,nativeQuery = true)
    int selectMaxSeq();

    LectureDivide getLectureDivideByLecture(Lecture lecture);
}
