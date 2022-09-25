package com.edo.lecture.repository;

import com.edo.lecture.entity.Lecture;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,Long> {
    Lecture findByLectureTitle(String LectureTitle);
}
