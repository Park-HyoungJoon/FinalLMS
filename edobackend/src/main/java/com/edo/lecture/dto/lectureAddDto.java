package com.edo.lecture.dto;

import com.edo.lecture.entity.Lecture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class lectureAddDto {
    String lectureTitle;
    String lecturePart;
    String lectureInfo;
    String lectureDetail;
    String startDateYear;
    String startDateMonth;
    String startDateDay;
    String finalDateYear;
    String finalDateMonth;
    String finalDateDay;
    String subyn;
    String manageStartDateYear;
    String manageStartDateMonth;
    String manageStartDateDay;
    String manageFinalDateYear;
    String manageFinalDateMonth;
    String manageFinalDateDay;
    String manageyn;
    String lectureTime;
    String lectureImg;
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
    public LocalDate StrToTime(String year, String month, String day){
        String time = year+"-"+month+"-"+day;
        LocalDate date = LocalDate.parse(time, DateTimeFormatter.ISO_LOCAL_DATE);
        return date;
    }
    public Lecture lectureAddDtoToLecture(lectureAddDto lectureAddDto){
        return Lecture.builder().lectureYN(lectureAddDto.lectureyn)
                .lectureDetail(lectureAddDto.lectureDetail)
                .lectureInfo(lectureAddDto.lectureInfo)
                .lecturePart(lectureAddDto.lecturePart)
                .lectureTime(lectureAddDto.lectureTime)
                .lectureTitle(lectureAddDto.lectureTitle)
                .lectureImage(lectureAddDto.lectureImg)

                .startDate(lectureAddDto.startDate)
                .finalDate(lectureAddDto.finalDate)
                .manageStartDate(lectureAddDto.manageStartDate)
                .manageFinalDate(lectureAddDto.manageFinalDate)
                .subYN(lectureAddDto.subyn)
                .manageYN(lectureAddDto.manageyn)
                .build();
    }
}

