package com.edo.lecture.dto;

import com.edo.community.dto.CommunityTestDto;
import com.edo.community.entity.CommunityTest;
import com.edo.lecture.entity.Lecture;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * StrToTime 객체는 lectureAdd.html에서 select 태그의 정보를 하나의 LocalDate로 합치기 위한 객체다.
 * lectureAdd.html 에서 select 태그는 year, month, day로 나뉘어져 있는데 이것을 하나의 String 변수로
 * 합친 후 해당 변수를 LocalDate 타입으로 변경한다.
 *
 *LectureAddDtoToLecture는 LectureAddDto를 매개변수로 받아 LectureAddDto를 Lecture Entity에 맞게
 * Builder를 사용하여 변경한다.
 *
 * RealLectureImg와 LectureImg를 나눈 이유
 * html단에서 값이 넘어오는 부분은 File 타입으로 RealLectureImg 변수에 저장된다.
 * 하지만 DB는 파일 타입이 아닌 경로만 저장할 예정이기에 LectureController에서
 * RealLectureImg.getOriginalFilename()으로 LectureImg에 저장한다.
 *
 * 해당 이름이 겹치는 경우는 추후에 UUID를 사용해 변경할 예정이다.
 */
@Getter
@Setter
public class LectureAddDto {
    Long id;
    @JsonProperty("thisId")
    String thisId;
    String lectureTitle;
    String part;
    String lectureInfo;
    String lectureDetail;
    boolean subyn;
    String startDateAndfinalDate;
    String manageStartDateAndmanageFinalDate;
    boolean manageyn;
    String lectureTime;
    String lectureImage;
    String teacherName;

    String teacherInfo;
    String teacherImg;
    String approval;
    LocalDate startDate;
    LocalDate finalDate;
    LocalDate manageStartDate;
    LocalDate manageFinalDate;
    String lectureyn;
    MultipartFile realLectureImg;
    MultipartFile realTeacherImg;
    @JsonProperty("lectureInfoHidden")
    String lectureInfoHidden;
    @JsonProperty("lectureImgStr")
    String lectureImgStr;
    public List<LocalDate> StrToTime(String data){
        String[] str = data.split(" ");
        List<LocalDate> date = new ArrayList<>();
        date.add(LocalDate.parse(str[0], DateTimeFormatter.ISO_LOCAL_DATE));
        date.add(LocalDate.parse(str[2], DateTimeFormatter.ISO_LOCAL_DATE));
        return date;
    }

    private static ModelMapper modelMapper = new ModelMapper();

    public Lecture dtoToLecture() { return modelMapper.map(this,Lecture.class);}

    public static LectureAddDto of (Lecture lecture){
        return modelMapper.map(lecture,LectureAddDto.class);
    }
}

