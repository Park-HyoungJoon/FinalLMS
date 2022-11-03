package com.edo.lecture.repository;

import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureDivide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LectureDivideRepository extends JpaRepository<LectureDivide,Long> {
    @Query(value = "SELECT MAX(lecture_divide_seq) from lecture_divide" ,nativeQuery = true)
    int selectMaxSeq();


    @Modifying
    @Transactional
    @Query(value = "INSERT into lecture_divide (lecture_id) values (?1)" , nativeQuery = true)
    void insertFirstDivide(Long id);
    List<LectureDivide> getLectureDivideByLecture(Lecture lecture);

    LectureDivide getLectureDivideById(Long id);

    @Query(value = "SELECT l.lecture_divide_id from lecture_divide l where l.lecture_id=?1 order by l.lecture_divide_id asc", nativeQuery = true)
    List<Long>  getLectureDivideIdsByLecture(Long id);

    @Query(value = "SELECT l.lecture_id from lecture_divide l where l.lecture_divide_id=?1",nativeQuery = true)
    Long getLectureByDivideId(Long id);

    @Query(value = "select MAX(lecture_divide_id) from lecture_divide",nativeQuery = true)
    LectureDivide getFinalDivide();
}
