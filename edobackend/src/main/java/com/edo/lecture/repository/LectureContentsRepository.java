package com.edo.lecture.repository;

import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureDivide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureContentsRepository extends JpaRepository<LectureContents,Long> {

    @Query(value = "select MAX(lecture_contents_id) from lecture_contents ORDER BY lecture_contents_id desc",nativeQuery = true)
    Long newContents();

    @Query(value = "select lecture_contents_id from lecture_contents where lecture_divide_id=?1",nativeQuery = true)
    List<Long> findLectureContentsIdByLectureDivide(Long id);
    @Modifying
    @Transactional
    @Query(value = "delete from lecture_contents where lecture_divide_id = ?1", nativeQuery = true)
    boolean deleteContentsByDivideId(Long id);

    @Modifying
    @Transactional
    void deleteLectureContentsById(Long id);

    List<LectureContents> findAllByLectureDivide(LectureDivide lectureDivide);
}
