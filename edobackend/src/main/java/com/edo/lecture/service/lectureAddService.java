package com.edo.lecture.service;

import com.edo.lecture.dto.lectureAddDto;
import com.edo.lecture.entity.Lecture;
import com.edo.lecture.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class lectureAddService {
    @Autowired
    LectureRepository lectureRepository;

    public String lectureAdd(lectureAddDto lectureAddDto){
        Lecture lecture = lectureAddDto.lectureAddDtoToLecture(lectureAddDto);
        lectureRepository.save(lecture);
        return "잘 보내진 듯 ";
    }
}
