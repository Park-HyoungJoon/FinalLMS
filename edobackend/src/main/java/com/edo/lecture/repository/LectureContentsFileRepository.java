package com.edo.lecture.repository;

import com.edo.lecture.entity.LectureContentsFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureContentsFileRepository extends JpaRepository<LectureContentsFile,Long> {

}
