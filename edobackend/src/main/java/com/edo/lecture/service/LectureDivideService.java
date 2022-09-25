package com.edo.lecture.service;

import com.edo.lecture.entity.LectureDivide;
import com.edo.lecture.repository.LectureDivideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureDivideService {
    @Autowired
    LectureDivideRepository lectureDivideRepository;

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

    public void save(LectureDivide lectureDivide) {
        lectureDivideRepository.save(lectureDivide);
    }
}
