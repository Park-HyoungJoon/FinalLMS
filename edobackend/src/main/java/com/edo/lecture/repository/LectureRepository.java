package com.edo.lecture.repository;

import com.edo.lecture.entity.Lecture;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,Long> {
    @Modifying
    @Transactional
    void deleteLectureById(Long id);
    Page<Lecture> findAll(Pageable request);
    Page<Lecture> findAllByLecturePart(Pageable request,String part);
    Lecture findByLectureTitle(String LectureTitle);
    @Query(value = "select * from lecture order by lecture_id desc LIMIT ?1" ,nativeQuery = true)
    List<Lecture> findAllLectureTopFour(int limit_num);

    @Query(value = "select * from lecture where lecture_id=?1",nativeQuery = true)
    Lecture findLectureById(Long id);
}
