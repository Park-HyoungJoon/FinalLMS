package com.edo.lecture.dto;

import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureDivide;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LectureContentsDto {
    String lectrueContentsTitle;
    String lectureContentsInfo;
    LectureDivide lectureDivide;

   public LectureContents lectureContentsDtoTolectureContents (LectureContentsDto lectureContentsDto){
        return LectureContents.builder().lectureContentsTitle(lectureContentsDto.lectrueContentsTitle)
                .lectureDivide(lectureContentsDto.lectureDivide)
                .lectureContentsInfo(lectureContentsDto.lectureContentsInfo)
                .build();
    }
}
