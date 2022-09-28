package com.edo.lecture.service;

import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureContentsFile;
import com.edo.lecture.repository.LectureContentsFileRepository;
import com.edo.lecture.repository.LectureContentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureContentsService {
    @Autowired
    LectureContentsRepository lectureContentsRepository;

    @Autowired
    LectureContentsFileRepository lectureContentsFileRepository;
    public  void save(String originalFileName) {
    }

    public LectureContents save(LectureContents lectureContents) {
        return lectureContentsRepository.save(lectureContents);
    }

    //Contents 가장 최근 ID값 검색
    public Long getNewContents(){return lectureContentsRepository.newContents();}

    public LectureContents getContentsById(long id){
        return lectureContentsRepository.findById(id).get();
    }
    public void save(LectureContentsFile lectureContentsFile){lectureContentsFileRepository.save(lectureContentsFile);}
}
