package com.edo.lecture.repository;

import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureDivide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureContentsRepository extends JpaRepository<LectureContents,Long> {

    @Query(value = "select MAX(lecture_contents_id) from lecture_contents ORDER BY lecture_contents_id desc",nativeQuery = true)
    Long newContents();

    List<LectureContents> findAllByLectureDivide(LectureDivide lectureDivide);
}
