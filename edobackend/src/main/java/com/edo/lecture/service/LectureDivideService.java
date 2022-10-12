package com.edo.lecture.service;

import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureDivide;
import com.edo.lecture.repository.LectureDivideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureDivideService {
    @Autowired
    LectureDivideRepository lectureDivideRepository;

    public List<LectureDivide> getLectureDivideByLecture(Lecture lecture){
        List<LectureDivide> lectureDivide = lectureDivideRepository.getLectureDivideByLecture(lecture);
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

    public LectureDivide save(LectureDivide lectureDivide) {

        return lectureDivideRepository.save(lectureDivide);
    }
}
