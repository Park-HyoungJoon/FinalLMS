package com.edo.lecture.dto;

import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureDivide;
import com.edo.lecture.service.LectureDivideService;
import com.edo.lecture.service.LectureService;
import com.edo.util.ApplicationContext.BeanUtils;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;

@Getter
@Setter
@Component
public class LectureDivideDto {
    @Autowired
    LectureDivideService lectureDivideService;

    @Autowired
    LectureService lectureService;

    private Lecture lecture;

    //차시제목
    private String lectureDivideTitle;

    //차시순번
    private int lectureDivideSeq;

    //Lecture를 구하기 위한 lectureTitle
    private Long lectureId;

    //차시 순번 가져오기
    public int SearchSeq(){
        lectureDivideService = BeanUtils.getBean(LectureDivideService.class);
        int selectSeq = lectureDivideService.selectSeq();
        return selectSeq;
    }
    private static ModelMapper modelMapper = new ModelMapper();

    public LectureDivide dtoToLectureContents(LectureDivideDto lectureDivideDto) {
        int MaxSeq = SearchSeq();
        lectureService = BeanUtils.getBean(LectureService.class);
        Lecture lecture1 = lectureService.getLectureById(lectureDivideDto.getLectureId());
        this.setLecture(lecture1);
        this.setLectureDivideSeq(MaxSeq);
        return modelMapper.map(this,LectureDivide.class);}

    public static LectureDivideDto of (LectureDivide lectureDivide){
        return modelMapper.map(lectureDivide,LectureDivideDto.class);
    }
}
