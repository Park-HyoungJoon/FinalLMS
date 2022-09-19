package com.edo.lecture.controller;

import com.edo.lecture.dto.LectureAddDto;
import com.edo.lecture.service.LectureAddService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class LectureController {
    @Value("${part4.upload.path}")
    private String filePath;
    @Autowired
    LectureAddService lectureAddService;

    @GetMapping(value="/lecture")
    public String Home(){
        return "lecture/lecture";
    }

    @GetMapping(value="/lecture/add")
    public String lectureAdd2() {
        return "lecture/lectureAdd";
    }

    /**
     *lectureAdd.html에서 강의 추가 정보를 가져온 후 강의 차시로 넘어가는 객체다.
     *
     * @param lectureAddDto
     * LectureAddDto는 LectureAdd에서 가져온 정보를 Lecture Entity의 모습으로 변경한 후
     * 저장하기 위해 사용된다.
     * @param model
     * @return
     * @throws IOException
     */
    @PostMapping(value="/lecture/add" , consumes = "multipart/form-data")
    public String lectureAdd(@ModelAttribute LectureAddDto lectureAddDto, Model model) throws IOException {
        /**
         * 접수기간
         * StrToTime 설명은 lectureAddDto 클래스에 있다.
         * 쉽게 말하면 html단에 year , month, day 정보를 가져와 year-month-day 형태로 바꾼 후
         * LocalDate 타입으로 변환하는 메서드다.
         *
         * subyn은 접수기간의 상시체크
         */
        LocalDate startTime = lectureAddDto.StrToTime(lectureAddDto.getStartDateYear(), lectureAddDto.getStartDateMonth(), lectureAddDto.getStartDateDay());
        LocalDate finalTime = lectureAddDto.StrToTime(lectureAddDto.getFinalDateYear(), lectureAddDto.getFinalDateMonth(), lectureAddDto.getFinalDateDay());
        String subyn = lectureAddDto.getSubyn();

        /**
         * 운영기간
         * manageyn은 운영기간의 상시체크
         */
        LocalDate manageStartTime = lectureAddDto.StrToTime(lectureAddDto.getManageStartDateYear(), lectureAddDto.getManageStartDateMonth(), lectureAddDto.getManageStartDateDay());
        LocalDate manageFinalTime = lectureAddDto.StrToTime(lectureAddDto.getManageFinalDateYear(), lectureAddDto.getManageFinalDateMonth(), lectureAddDto.getManageFinalDateDay());
        String manageyn = lectureAddDto.getManageyn();

        /**
         * resourcePath는 현재 프로젝트의 상대경로를 가져와 그 안에
         * resources폴더까지의 경로이다.
         *
         * file
         */
        String resourcePath = System.getProperty("user.dir")+"/src/main/resources";
        File file = new File(resourcePath+filePath+lectureAddDto.getRealLectureImg().getOriginalFilename());
        File Folder = new File(resourcePath+filePath);
        if(!Folder.exists()){
            Folder.mkdir();
        }
        log.info(resourcePath+filePath+lectureAddDto.getRealLectureImg().getOriginalFilename());
        lectureAddDto.getRealLectureImg().transferTo(file);
        //Lecture 테이블에 LecutreImg 필드에 해당 이미지의 경로를 넣기 위해 setLectureImg를 통해 저장
        lectureAddDto.setLectureImg(lectureAddDto.getRealLectureImg().getOriginalFilename());
        file = new File(resourcePath+filePath+lectureAddDto.getRealTeacherImg().getOriginalFilename());
        lectureAddDto.getRealTeacherImg().transferTo(file);
        lectureAddDto.setTeacherImg(lectureAddDto.getRealTeacherImg().getOriginalFilename());

        lectureAddDto.setStartDate(startTime);
        lectureAddDto.setFinalDate(finalTime);
        lectureAddDto.setManageStartDate(manageStartTime);
        lectureAddDto.setManageFinalDate(manageFinalTime);
        lectureAddService.lectureAdd(lectureAddDto);
        //File dwst = new File("D:/file/"+lectureAdd.getLectureImg());
        //lectureAdd.getLectureImg().tra

        return "lecture/lectureContents";
    }

    @GetMapping(value="/lecture/contents")
    public String lectureContents() {
        return "/lecture/lectureContents";
    }

}