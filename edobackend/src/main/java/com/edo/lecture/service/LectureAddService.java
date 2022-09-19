package com.edo.lecture.service;

import com.edo.lecture.dto.LectureAddDto;
import com.edo.lecture.entity.Lecture;
import com.edo.lecture.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureAddService {
    @Autowired
    LectureRepository lectureRepository;

    public String lectureAdd(LectureAddDto lectureAddDto){
        Lecture lecture = lectureAddDto.LectureAddDtoToLecture(lectureAddDto);
        lectureRepository.save(lecture);
        return "잘 보내진 듯 ";
    }
}
