package com.edo.lecture.service;

import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureContentsFile;
import com.edo.lecture.entity.LectureDivide;
import com.edo.lecture.repository.LectureContentsFileRepository;
import com.edo.lecture.repository.LectureContentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LectureContentsService {
    @Autowired
    LectureContentsRepository lectureContentsRepository;

    @Autowired
    LectureContentsFileRepository lectureContentsFileRepository;
    public  void save(String originalFileName) {
    }
    public void removeLectureContents(Long id){
        lectureContentsRepository.deleteLectureContentsById(id);
    }

    public LectureContents save(LectureContents lectureContents) {
        return lectureContentsRepository.save(lectureContents);
    }
    public List<LectureContents> getLectureContentsList(LectureDivide lectureDivide){
        List<LectureContents> lectureContentsList = lectureContentsRepository.findAllByLectureDivide(lectureDivide);
        return lectureContentsList;
    }
    public List<Long> getContentsIdsByDivideId(Long divideId){
        List<Long> ids = lectureContentsRepository.findLectureContentsIdByLectureDivide(divideId);
        return ids;
    }
    public LectureContentsFile getLectureContentsFileByLectureContents(LectureContents lectureContents) {
        LectureContentsFile lectureContentsFile = lectureContentsFileRepository.findByLectureContents(lectureContents);
        return lectureContentsFile;
    }

    //Contents 가장 최근 ID값 검색
    public Long getNewContents(){return lectureContentsRepository.newContents();}

    public LectureContents getContentsById(long id){
        return lectureContentsRepository.findById(id).get();
    }
    public void save(LectureContentsFile lectureContentsFile){lectureContentsFileRepository.save(lectureContentsFile);}

    public void removeAll(List<Long> contentsId) {
        for(Long id : contentsId){
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!!!!!"+id);
            try {
                lectureContentsFileRepository.deleteContentsFileByContentsId(id);
                lectureContentsRepository.deleteById(id);
            }catch (Exception e){}
        }
       }
}
