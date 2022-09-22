package com.edo.lecture.dto;

import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureDivide;
import org.springframework.web.multipart.MultipartFile;

public class LectureContentsDto {
    String lectrueContentsTitle;
    String lectureContentsFile;
    LectureDivide lectureDivide;
    MultipartFile ealLectureContentsFile;

    public LectureContents lectureContentsDtoTolectureContents (LectureContentsDto lectureContentsDto){
        return LectureContents.builder().lectureContentsTitle(lectureContentsDto.lectrueContentsTitle)
                .lectureContentsFileName(lectureContentsDto.lectureContentsFile)
                .lectureDivide(lectureContentsDto.lectureDivide).build();
    }
}
