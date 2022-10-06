package com.edo.lecture.dto;

import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureDivide;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LectureContentsDto {
    String lectrueContentsTitle;
    String lectureContentsInfo;
    LectureDivide lectureDivide;

    private static ModelMapper modelMapper = new ModelMapper();

    public LectureContents dtoToLectureContents() { return modelMapper.map(this,LectureContents.class);}

    public static LectureContentsDto of (LectureContents lectureContents){
        return modelMapper.map(lectureContents,LectureContentsDto.class);
    }
}
