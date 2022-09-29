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
    public String Lecture(Model model){

        return "lecture/lecture";
    }

    @GetMapping(value="/lecture/add")
    public String LectureAdd2(Model model)
    {
        model.addAttribute("lectureAddDto",new LectureAddDto());
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
        log.info("#??????????????????????????????????????????"+lectureAddDto.isSubyn());
        log.info("#><>@>>#@?#>@?>#?@>#?>@?>#?@>#?@>>#?@>#?"+lectureAddDto.getApproval());
        log.info("#L#PKP#KO#KO#KO#KO#KOK#OKO#KO#KO#KOK#OK#OK"+lectureAddDto.getPart());
        log.info("#RKEOKREORKEOKREOKREOKROKEOREOKROEKROE"+lectureAddDto.getLectureInfoHidden());

        List<LocalDate> subDate = lectureAddDto.StrToTime(lectureAddDto.getStartDateAndfinalDate());
        List<LocalDate> manageDate = lectureAddDto.StrToTime(lectureAddDto.getManageStartDateAndmanageFinalDate());

        /**
         * resourcePath는 현재 프로젝트의 상대경로를 가져와 그 안에
         * resources폴더까지의 경로이다.
         *
         * file
         */
        String resourcePath = System.getProperty("user.dir")+"/src/main/resources";
        UUID uuid = UUID.randomUUID();
        String uuidFileName = uuid+"_"+lectureAddDto.getRealLectureImg().getOriginalFilename();
        File file = new File(resourcePath+imgRoot+uuidFileName);
        File Folder = new File(resourcePath+imgRoot);
        if(!Folder.exists()){
            Folder.mkdir();
        }
        //lectureAddDto.getRealLectureImg를 통해 받아온 MultiPartFile을 file에 지정된 경로로 보낸다.
        lectureAddDto.getRealLectureImg().transferTo(file);
        String uuidFileName2 = uuid+"_"+lectureAddDto.getRealTeacherImg().getOriginalFilename();
        file = new File(resourcePath+imgRoot+uuidFileName2);
        //Entity transfer(파일)
        lectureAddDto.getRealTeacherImg().transferTo(file);


        //강좌 생성 내용
        //Lecture 테이블에 LecutreImg 필드에 해당 이미지의 경로를 넣기 위해 setLectureImg를 통해 저장
        lectureAddDto.setLectureImg(uuidFileName);
        lectureAddDto.setLectureTitle(lectureAddDto.getLectureTitle());
        lectureAddDto.setPart(lectureAddDto.getPart());
        lectureAddDto.setLectureTime(lectureAddDto.getLectureTime());
        lectureAddDto.setSubyn(lectureAddDto.isSubyn());
        lectureAddDto.setManageyn(lectureAddDto.isManageyn());
        lectureAddDto.setLectureDetail(lectureAddDto.getLectureDetail());
        lectureAddDto.setLectureInfo(lectureAddDto.getLectureInfoHidden());
        lectureAddDto.setStartDate(subDate.get(0));
        lectureAddDto.setFinalDate(subDate.get(1));
        lectureAddDto.setManageStartDate(manageDate.get(0));
        lectureAddDto.setManageFinalDate(manageDate.get(1));

        //선생 업데이트 내용
        lectureAddDto.setTeacherImg(uuidFileName2);

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

    /***
     * lectureContents.html에서 form으로 파일 데이터를 받아 LectureContentsFile Entity에 저장해놓는 컨트롤러이다.
     *
     * LectureContensFile에는 각 차시 별 파일에 대한 정보가 들어가야 한다.
     * 차시에 따라 파일 정보가 들어가야 하므로 contents_id가 필요하다.
     * contents_id는 lectureContentsService.getNewContents를 통해 가장 마지막에 저장되어있는 contents_id값을 가져온 후
     * 해당 메서드 실행 전에 실행된 메서드인 lectureDivideAndContentsAdd에서
     model.addAttribute("getSize",lectureContents3.size());
     값을 갖고와 newContents-(getSize-i) 를 통해 최근 순으로 하나씩 값을 넣어주는 방식으로 해결했다.

     나머지는 파일에 대한 정보를 넣는 코드이다.
     * @param fileVO
     * fileVO는 LectoureContentsFile Entity에 저장하기 전 데이터를 담아놓는 객체이다.
     * @return
     * @throws IOException
     */
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