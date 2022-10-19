package com.edo.lecture.repository;

import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureContentsFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface LectureContentsFileRepository extends JpaRepository<LectureContentsFile,Long> {
    LectureContentsFile findByLectureContents(LectureContents lectureContents);
    @Modifying
    @Transactional
    @Query(value = "delete from lecture_contents_file where lecture_contents_id = ?1", nativeQuery = true)
    void deleteContentsFileByContentsId(Long id);


}
