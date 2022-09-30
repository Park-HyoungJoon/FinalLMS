package com.edo.lecture.repository;

import com.edo.lecture.entity.Lecture;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,Long> {
    Page<Lecture> findAll(Pageable request);
    Page<Lecture> findAllByLecturePart(Pageable request,String part);
    Lecture findByLectureTitle(String LectureTitle);
}
