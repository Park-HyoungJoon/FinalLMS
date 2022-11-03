package com.edo.lecture.service;

import com.edo.lecture.dto.LectureDivideAndContentsDto;
import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureDivide;
import com.edo.lecture.repository.LectureDivideRepository;
import com.edo.lecture.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectureDivideService {
    @Autowired
    LectureDivideRepository lectureDivideRepository;
    @Autowired
    LectureService lectureService;

    @Autowired
    LectureRepository lectureRepository;
    public LectureDivide getLectureDivideFirstByLecture(Lecture lecture){
        LectureDivide lectureDivide = lectureDivideRepository.getLectureDivideByLecture(lecture).get(0);
        return lectureDivide;
    }
    public List<Long> getLectureDivideIds(Long id){
        List<Long> lectureDivideIds = lectureDivideRepository.getLectureDivideIdsByLecture(id);
        return lectureDivideIds;
    }
    public int selectSeq() {
        int seq = 0;
        try{
            seq = lectureDivideRepository.selectMaxSeq();
            System.out.println(seq);
            return seq+1;

        }catch (Exception e){
            seq =1;
            return seq;
        }
    }

    public LectureDivide getLectureDivideById(Long id){
        LectureDivide lectureDivide = lectureDivideRepository.getLectureDivideById(id);
        return lectureDivide;
    }

    public LectureDivide save(LectureDivide lectureDivide) {

        return lectureDivideRepository.save(lectureDivide);
    }

    public LectureDivide addDivide(Long id) {
        Lecture lecture = lectureService.getLectureById(id);
        LectureDivide lectureDivide = new LectureDivide();
        lectureDivide.setLecture(lecture);
        LectureDivide lectureDivide1 = lectureDivideRepository.save(lectureDivide);
    return lectureDivide1;
    }

    public void deleteDivide(Long id) {
        LectureDivide lectureDivide = lectureDivideRepository.getLectureDivideById(id);
        lectureDivideRepository.delete(lectureDivide);
    }

    public Lecture getLecture(Long id) {
        Long lectureId = lectureDivideRepository.getLectureByDivideId(id);
        Lecture lecture = lectureRepository.findLectureById(lectureId);
        return lecture;
    }
    public List<LectureDivide> getListDivide(Lecture lecture){
        List<LectureDivide> lectureDivideList = lectureDivideRepository.getLectureDivideByLecture(lecture);
        return lectureDivideList;
    }

    public LectureDivide insertFirstDivide(Long id) {
        LectureDivideAndContentsDto lectureDivideAndContentsDto = new LectureDivideAndContentsDto();
        lectureDivideAndContentsDto.setLectureId(id);
        LectureDivide lectureDivide = lectureDivideAndContentsDto.dtoToLectureDivide(lectureDivideAndContentsDto);
        return lectureDivide;
    }
}
