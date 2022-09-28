package com.edo.lecture.controller;

import com.edo.lecture.dto.*;
import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureContentsFile;
import com.edo.lecture.entity.LectureDivide;
import com.edo.lecture.service.LectureDivideService;
import com.edo.lecture.service.LectureService;
import com.edo.lecture.service.LectureContentsService;
import com.edo.util.fileDTO.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@Log4j2
public class LectureController {
    @Value("${part4.upload.path}")
    private String imgRoot;
    @Value("${part5.upload.path}")
    private String fileRoot;
    @Autowired
    LectureService lectureService;
    @Autowired
    LectureDivideService lectureDivideService;

    @Autowired
    LectureContentsService lectureContentsService;

    @GetMapping(value="/lecture")
    public String Home(){
        return "lecture/lecture";
    }

    @GetMapping(value="/lecture/add")
    public String LectureAdd2() {
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
    public String LectureAdd(@ModelAttribute LectureAddDto lectureAddDto, Model model) throws IOException {
        /**
         * 접수기간
         * StrToTime 설명은 lectureAddDto 클래스에 있다.
         * 쉽게 말하면 html단에 year , month, day 정보를 가져와 year-month-day 형태로 바꾼 후
         * LocalDate 타입으로 변환하는 메서드다.
         *
         * subyn은 접수기간의 상시체크
         */
        log.info(lectureAddDto.getStartDateAndfinalDate());
        List<LocalDate> subDate = lectureAddDto.StrToTime(lectureAddDto.getStartDateAndfinalDate());
        List<LocalDate> manageDate = lectureAddDto.StrToTime(lectureAddDto.getManageStartDateAndmanageFinalDate());

        /**
         * resourcePath는 현재 프로젝트의 상대경로를 가져와 그 안에
         * resources폴더까지의 경로이다.
         *
         * file
         */
        String resourcePath = System.getProperty("user.dir")+"/src/main/resources";
        File file = new File(resourcePath+imgRoot+lectureAddDto.getRealLectureImg().getOriginalFilename());
        File Folder = new File(resourcePath+imgRoot);
        if(!Folder.exists()){
            Folder.mkdir();
        }
        log.info(resourcePath+imgRoot+lectureAddDto.getRealLectureImg().getOriginalFilename());
        //lectureAddDto.getRealLectureImg를 통해 받아온 MultiPartFile을 file에 지정된 경로로 보낸다.
        lectureAddDto.getRealLectureImg().transferTo(file);
        //Lecture 테이블에 LecutreImg 필드에 해당 이미지의 경로를 넣기 위해 setLectureImg를 통해 저장
        lectureAddDto.setLectureImg(lectureAddDto.getRealLectureImg().getOriginalFilename());
        file = new File(resourcePath+imgRoot+lectureAddDto.getRealTeacherImg().getOriginalFilename());
        //Entity transfer(파일)
        lectureAddDto.getRealTeacherImg().transferTo(file);
        lectureAddDto.setTeacherImg(lectureAddDto.getRealTeacherImg().getOriginalFilename());

        lectureAddDto.setLectureInfo(lectureAddDto.getLectureInfo());
        log.info("?/////////////////////////////////////"+lectureAddDto.getLectureInfo());


        lectureAddDto.setStartDate(subDate.get(0));
        lectureAddDto.setFinalDate(subDate.get(1));
        lectureAddDto.setManageStartDate(manageDate.get(0));
        lectureAddDto.setManageFinalDate(manageDate.get(1));

        lectureService.lectureAdd(lectureAddDto);

        model.addAttribute("LectureTitle",lectureAddDto.getLectureTitle());
        return "lecture/lectureContents";
    }

    @GetMapping(value="/lecture/contents")
    public String LectureContents(@RequestParam String lectureTitle)
    {
        return "/lecture/lectureContents";
    }

    @PostMapping(value="/lecture/contents" )
    public String LectureDivideAndContentsAdd(@RequestBody LectureContentsAddDto lectureContentsAddDto,Model model) {
        LectureDivideDto lectureDivideDto = new LectureDivideDto();
        LectureContentsDto lectureContentsDto = new LectureContentsDto();
        lectureDivideDto.setLectureTitle(lectureContentsAddDto.getLectureTitle());
        LectureDivide lectureDivide = lectureDivideDto.lectureDivideDtoTolectureDivide(lectureDivideDto);
        LectureDivide getLectureDivide = lectureDivideService.save(lectureDivide);
        List<LectureContents> lectureContents3 = new ArrayList<>();
        for (int i=0; i<lectureContentsAddDto.getLectureContentsInfo().length; i++){
            String lectureContentsInfo = lectureContentsAddDto.getLectureContentsInfo()[i];
            String lectureContentsTitle = lectureContentsAddDto.getLectureContentsTitle()[i];
            lectureContentsDto.setLectureDivide(getLectureDivide);
            lectureContentsDto.setLectureContentsInfo(lectureContentsInfo);
            lectureContentsDto.setLectrueContentsTitle(lectureContentsTitle);
            LectureContents lectureContents = lectureContentsDto.lectureContentsDtoTolectureContents(lectureContentsDto);
           lectureContents3.add(lectureContentsService.save(lectureContents));
        }
        lectureContents3.size();
        log.info("////////////////////////////"+lectureContents3.size());
        log.info("///////////2/"+lectureContents3.get(0).getId());
        List<GetIdDto> getId = new ArrayList<>();
        GetIdDto getIdDto = new GetIdDto();
        for (int i=0; i<lectureContents3.size(); i++){
            getIdDto.setGetId(lectureContents3.get(i).getId());
            getId.add(getIdDto);
        }
        model.addAttribute("getSize",lectureContents3.size());
        model.addAttribute("getId",getId);
        return "lecture/lectureContents";
    }
    @GetMapping(value = "/contents/uploader")
    public void ContentsFileCreate(){

    }
    @PostMapping(value="/contents/uploader", consumes = "multipart/form-data")
            public String ContentsFileCreate(FileVO fileVO)
            throws IOException {
        Long newContents = lectureContentsService.getNewContents();
        int idx = Integer.parseInt(fileVO.getContentsSeq());
        String resourcePath = fileRoot;
            //uuid를 통한 랜덤값 지정
            UUID uuid = UUID.randomUUID();
        List<MultipartFile> files = fileVO.ContentsFileToList(fileVO);
            for (int i=1; i<=idx; i++){
                log.info("#?///////////////////////////////////////"+newContents);
                log.info("#?@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+idx+"i값"+i);
                if(files.get(i-1)==null){
                    continue;
                }
                String uuidFileName = uuid+"_"+files.get(i-1).getOriginalFilename();
                File file = new File(fileRoot+uuidFileName);
                //file에 폴더 존재여부 확인
                File Folder = new File(resourcePath);
                if(!Folder.exists()){
                    Folder.mkdir();
                }
                files.get(i-1).transferTo(file);
                LectureContentsFileDto lectureContentsFileDto = new LectureContentsFileDto();
                lectureContentsFileDto.setUuidPath(uuidFileName);
                LectureContentsFile lectureContentsfile = lectureContentsFileDto.toLectureContentsFile(
                        files.get(i-1), lectureContentsService.getContentsById(newContents-(idx-i))
                );
                lectureContentsService.save(lectureContentsfile);
            }

        return "redirect:/lecture";
    }
}