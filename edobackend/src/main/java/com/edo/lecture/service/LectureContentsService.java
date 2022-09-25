package com.edo.lecture.service;

import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.repository.LectureContentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureContentsService {
    @Autowired
    LectureContentsRepository lectureContentsRepository;
    public  void save(String originalFileName) {
    }

    public void save(LectureContents lectureContents) {
        lectureContentsRepository.save(lectureContents);
    }
}
