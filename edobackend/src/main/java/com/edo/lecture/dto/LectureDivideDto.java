package com.edo.lecture.dto;

import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureDivide;
import com.edo.lecture.service.LectureDivideService;
import com.edo.lecture.service.LectureService;
import com.edo.util.ApplicationContext.BeanUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private String lectureTitle;

    //차시 순번 가져오기
    public int SearchSeq(){
        lectureDivideService = BeanUtils.getBean(LectureDivideService.class);
        int selectSeq = lectureDivideService.selectSeq();
        return selectSeq;
    }

    public LectureDivide lectureDivideDtoTolectureDivide(LectureDivideDto lectureDivideDto){
        int MaxSeq = SearchSeq();
        //lectureService가 의존성 주입이 제대로 먹지 않아 커스텀 클래스인 BeanUtils를 만들어 의존성 주입
        lectureService = BeanUtils.getBean(LectureService.class);
        Lecture lecture1 = lectureService.SearchLectureToTitle(lectureDivideDto.getLectureTitle());
        return LectureDivide.builder().
                lectureDivideTitle(lectureDivideDto.getLectureDivideTitle())
                .lectureDivideSeq(MaxSeq)
                .lecture(lecture1)
                .build();
    }
}
