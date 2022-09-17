package com.edo.lecture.controller;

import com.edo.lecture.dto.lectureAddDto;
import com.edo.lecture.service.lectureAddService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@Log4j2
public class lectureController {
    @Autowired
    lectureAddService lectureAddService;

    @GetMapping(value="/lecture")
    public String Home(){
        return "lecture/lecture";
    }

    @GetMapping(value="/lecture/add")
    public String lectureAdd2() {
        return "lecture/lectureAdd";
    }

    @PostMapping(value="/lecture/add" , consumes = "multipart/form-data")
    public String lectureAdd(@ModelAttribute lectureAddDto lectureAddDto, Model model) throws IOException {
       //접수시작,마감기간,접수 상시
        LocalDate startTime = lectureAddDto.StrToTime(lectureAddDto.getStartDateYear(), lectureAddDto.getStartDateMonth(), lectureAddDto.getStartDateDay());
        LocalDate finalTime = lectureAddDto.StrToTime(lectureAddDto.getFinalDateYear(), lectureAddDto.getFinalDateMonth(), lectureAddDto.getFinalDateDay());
        String subyn = lectureAddDto.getSubyn();
        //운영시작,마감기간,마감 상시
        LocalDate manageStartTime = lectureAddDto.StrToTime(lectureAddDto.getManageStartDateYear(), lectureAddDto.getManageStartDateMonth(), lectureAddDto.getManageStartDateDay());
        LocalDate manageFinalTime = lectureAddDto.StrToTime(lectureAddDto.getManageFinalDateYear(), lectureAddDto.getManageFinalDateMonth(), lectureAddDto.getManageFinalDateDay());
        String manageyn = lectureAddDto.getManageyn();
        //이미지 업로드 내용 저장
        File file = new File("D:/file/"+ lectureAddDto.getRealLectureImg().getOriginalFilename());
        lectureAddDto.getRealLectureImg().transferTo(file);
        lectureAddDto.setLectureImg(lectureAddDto.getRealLectureImg().getOriginalFilename());
        file = new File("D:/file/"+lectureAddDto.getRealTeacherImg().getOriginalFilename());
        lectureAddDto.getRealTeacherImg().transferTo(file);
        lectureAddDto.setTeacherImg(lectureAddDto.getRealTeacherImg().getOriginalFilename());

        lectureAddDto.setStartDate(startTime);
        lectureAddDto.setFinalDate(finalTime);
        lectureAddDto.setManageStartDate(manageStartTime);
        lectureAddDto.setManageFinalDate(manageFinalTime);
        lectureAddService.lectureAdd(lectureAddDto);
        //File dwst = new File("D:/file/"+lectureAdd.getLectureImg());
        //lectureAdd.getLectureImg().tra

        return "lecture/lectureAdd";
    }

    @GetMapping(value="/lecture/contents")
    public String lectureContents() {
        return "/lecture/lectureContents";
    }

}
/*
,
            @RequestParam(value="lectureTitle") String lectureTitle,
            @RequestParam(value="part") String part
            , @RequestParam(value="startDateYear") String startDateYear
            , @RequestParam(value = "startDateMonth") String startDateMonth
            , @RequestParam(value="startDateDay") String startDateDay
            , @RequestParam(value="finalDateYear") String finalDateYear
            , @RequestParam(value="finalDateMonth") String finalDateMonth
            , @RequestParam(value="finalDateDay") String finalDateDay
            , @RequestParam(value="subyn") String subyn
            , @RequestParam(value="manageStartDateYear") String manageStartDateYear
            , @RequestParam(value="manageStartDateMonth") String manageStartDateMonth
            , @RequestParam(value="manageStartDateDay") String manageStartDateDay
            , @RequestParam(value="manageFinalDateYear") String manageFinalDateYear
            , @RequestParam(value="manageFinalDateMonth") String manageFinalDateMonth
            , @RequestParam(value="manageFinalDateDay") String manageFinalDateDay
            , @RequestParam(value="manageyn") String manageyn
            , @RequestParam(value="lectureTime") String lectureTime
            , @RequestParam(value="lectureImg")File lectureImg
            , @RequestParam(value="lectureDetail") String lectureDetail
            , @RequestParam(value="teacherName") String teacherName
            , @RequestParam(value="teacherInfo") String teacherInfo
            , @RequestParam(value="teacherImg") File teacherImg
            , @RequestParam(value = "approval") String approval

* **/