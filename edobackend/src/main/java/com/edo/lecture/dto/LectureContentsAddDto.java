package com.edo.lecture.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureContentsAddDto {
    private String lectureTitle;
    private String lectureDivideTitle;
    private String[] lectureContentsTitle;
    private String[] lectureContentsInfo;
}
