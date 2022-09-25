package com.edo.lecture.service;

import com.edo.lecture.dto.LectureAddDto;
import com.edo.lecture.entity.Lecture;
import com.edo.lecture.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class LectureService {
    @Autowired
    LectureRepository lectureRepository;

    //LectureTitle을 이용하여 Lecture객체 얻기
    public Lecture SearchLectureToTitle(String LectureTitle){
        Lecture lecture = lectureRepository.findByLectureTitle(LectureTitle);
        return lecture;
    }

    public String lectureAdd(LectureAddDto lectureAddDto){
        Lecture lecture = lectureAddDto.LectureAddDtoToLecture(lectureAddDto);
        lectureRepository.save(lecture);
        return "잘 보내진 듯 ";
    }
}
