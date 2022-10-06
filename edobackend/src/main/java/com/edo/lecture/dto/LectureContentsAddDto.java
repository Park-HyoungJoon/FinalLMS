package com.edo.lecture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureContentsAddDto {
    private String lectureId;
    private String lectureDivideTitle;
    private String[] lectureContentsTitle;
    private String[] lectureContentsInfo;
}
