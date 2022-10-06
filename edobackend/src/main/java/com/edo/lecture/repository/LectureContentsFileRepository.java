package com.edo.lecture.repository;

import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureContentsFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureContentsFileRepository extends JpaRepository<LectureContentsFile,Long> {
    LectureContentsFile findByLectureContents(LectureContents lectureContents);
}
