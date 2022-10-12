package com.edo.lecture.controller;

import com.edo.lecture.dto.*;
import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureContentsFile;
import com.edo.lecture.entity.LectureDivide;
import com.edo.lecture.repository.LectureRepository;
import com.edo.lecture.service.LectureDivideService;
import com.edo.lecture.service.LectureService;
import com.edo.lecture.service.LectureContentsService;
import com.edo.util.file.FileVO;
import com.edo.util.file.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Autowired
    LectureRepository lectureRepository;
    @GetMapping(value="/lecture")
    public String Lecture
            (@RequestParam(value = "tabNum",required = false,defaultValue = "tab1") String tabNum,
                    @RequestParam(value = "pageNumber",required = false,defaultValue = "1") int pageNumber,
                          @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                              Model model){
            List<Lecture> lectureList = lectureRepository.findAll();
            model.addAttribute("posts",lectureService.getPage(pageNumber,size));
            model.addAttribute("python",lectureService.getPageByPart(pageNumber,size,"파이썬"));
            model.addAttribute("aiPage",lectureService.getPageByPart(pageNumber,size,"인공지능"));
            model.addAttribute("daPage",lectureService.getPageByPart(pageNumber,size,"데이터분석"));
            model.addAttribute("list",lectureList);
            model.addAttribute("tabNum",tabNum);

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
    public String LectureAdd(@ModelAttribute LectureAddDto lectureAddDto, Model model) throws IOException,
            NullPointerException {
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
        try {
            String resourcePath = System.getProperty("user.dir") + imgRoot;
            if(lectureAddDto.getLectureImgStr()!=null){
                File file = new File(resourcePath + lectureAddDto.getLectureImgStr());
                if(file.exists()){
                    file.delete();
                }
            }
            String uuid = UUID.randomUUID().toString();
            String uuidFileName = uuid + "_" + lectureAddDto.getRealLectureImg().getOriginalFilename();
            lectureAddDto.setLectureImage(uuidFileName);
            Path savePath = Paths.get(resourcePath + uuidFileName);
            File Folder = new File(resourcePath);
            if (!Folder.exists()) {
                Folder.mkdir();
            }
//        //lectureAddDto.getRealLectureImg를 통해 받아온 MultiPartFile을 file에 지정된 경로로 보낸다.
            lectureAddDto.getRealLectureImg().transferTo(savePath);
            File file = new File(resourcePath,uuidFileName);
            InputStream inputStream = new FileInputStream(file);
            file.delete();
            int width = 335;
            int height = 225;
            ImageUtils utils = new ImageUtils();
            BufferedImage resizeImage = utils.resize(inputStream, width, height);
            file.delete();
            ImageIO.write(resizeImage, "jpg", new File(resourcePath + uuidFileName));

            String uuidFileName2 = uuid + "_" + lectureAddDto.getRealTeacherImg().getOriginalFilename();
            lectureAddDto.setTeacherImg(uuidFileName2);
            file = new File(resourcePath , uuidFileName2);
            //Entity transfer(파일)
            lectureAddDto.getRealTeacherImg().transferTo(file);
            inputStream = new FileInputStream(file);
            resizeImage = utils.resize(inputStream, width, height);
            file.delete();
            ImageIO.write(resizeImage, "jpg", new File(resourcePath + uuidFileName2));
        } catch (Exception e) {
        } finally {
            //강좌 생성 내용
            //Lecture 테이블에 LecutreImg 필드에 해당 이미지의 경로를 넣기 위해 setLectureImg를 통해 저장
            if(lectureAddDto.getThisId()!=null) {
                Long id = Long.parseLong(lectureAddDto.getThisId());
                lectureAddDto.setId(id);
            }
            lectureAddDto.setSubyn(lectureAddDto.isSubyn());
            lectureAddDto.setManageyn(lectureAddDto.isManageyn());
            lectureAddDto.setLectureInfo(lectureAddDto.getLectureInfoHidden());
            lectureAddDto.setStartDate(subDate.get(0));
            lectureAddDto.setFinalDate(subDate.get(1));
            lectureAddDto.setManageStartDate(manageDate.get(0));
            lectureAddDto.setManageFinalDate(manageDate.get(1));


            Lecture lecture = lectureService.lectureAdd(lectureAddDto);
            model.addAttribute("lectureId",lecture.getId());
            if(lectureAddDto.getThisId()==null) {
                return "lecture/lectureContents";
            }
            else{
                Lecture lecture1 = lectureService.getLectureById(Long.parseLong(lectureAddDto.getThisId()));
                List<Long> lectureDivideIds = lectureDivideService.getLectureDivideIds(lecture1.getId());
                List<LectureDivide> lectureDivide = lectureDivideService.getLectureDivideByLecture(lecture1);
                List<LectureContents> lectureContentsList = lectureContentsService.getLectureContentsList(lectureDivide.get(0));
                model.addAttribute("lectureDivideIds",lectureDivideIds);
                model.addAttribute("lectureDivide",lectureDivide);
                model.addAttribute("lectureContentsList",lectureContentsList);
                return "lecture/lectureContentsEdit";
            }
        }
    }

    @PostMapping(value = "lecture/divide/edit/{id}")
    public String LectureContentsPartEdit(@PathVariable("id") String divideId){

        return "/lecture/lectureContentsEdit";
    }
    @GetMapping(value="/lecture/contents")
    public String LectureContents(@RequestParam String lectureTitle)
    {
        return "/lecture/lectureContents";
    }

    @PostMapping(value="/lecture/contents" )
    public String LectureDivideAndContentsAdd(@RequestBody LectureDivideAndContentsDto lectureContentsAddDto,Model model) {
        Long lectureId = Long.parseLong(lectureContentsAddDto.getStrLectureId());
        LectureDivideAndContentsDto lectureDivideDto = new LectureDivideAndContentsDto();
        lectureDivideDto.setLectureId(lectureId);
        lectureDivideDto.setLectureDivideTitle(lectureContentsAddDto.getLectureDivideTitle());
        LectureDivideAndContentsDto lectureContentsDto = new LectureDivideAndContentsDto();
        LectureDivide lectureDivide = lectureDivideDto.dtoToLectureDivide(lectureDivideDto);
        LectureDivide getLectureDivide = lectureDivideService.save(lectureDivide);
        List<LectureContents> lectureContents3 = new ArrayList<>();
        log.info("##################################### lecture/Contents");
        for (int i=0; i<lectureContentsAddDto.getListLectureContentsInfo().length; i++){
            String lectureContentsInfo = lectureContentsAddDto.getListLectureContentsInfo()[i];
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+lectureContentsInfo);
            String lectureContentsTitle = lectureContentsAddDto.getListLectureContentsTitle()[i];
            lectureContentsDto.setLectureDivide(getLectureDivide);
            lectureContentsDto.setLectureContentsInfo(lectureContentsInfo);
            lectureContentsDto.setLectureContentsTitle(lectureContentsTitle);
            LectureContents lectureContents = lectureContentsDto.dtoToLectureContents();
           lectureContents3.add(lectureContentsService.save(lectureContents));
        }
        lectureContents3.size();
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
                LectureDivideAndContentsDto lectureContentsFileDto = new LectureDivideAndContentsDto();
                lectureContentsFileDto.setUuidPath(uuidFileName);
                LectureContentsFile lectureContentsfile = lectureContentsFileDto.toLectureContentsFile(
                        files.get(i-1), lectureContentsService.getContentsById(newContents-(idx-i))
                );
                lectureContentsService.save(lectureContentsfile);
            }

        return "redirect:/lecture";
    }


    @GetMapping(value = "/lecture/lectureDetail/{id}")
    public String goDetail(@PathVariable("id") Long id,Model model){
        log.info("??????????????????????????????????????????.//"+System.getProperty("user.dir"));
        Lecture lecture = lectureService.getLectureById(id);
        List<LectureDivide> lectureDivide = lectureDivideService.getLectureDivideByLecture(lecture);
        List<LectureContents> lectureContents = lectureContentsService.getLectureContentsList(lectureDivide.get(0));
        List<LectureContentsFile> lectureContentsFileList = new ArrayList<>();
        for (LectureContents lectureContents1 : lectureContents){
            LectureContentsFile lectureContentsFile = lectureContentsService.getLectureContentsFileByLectureContents(lectureContents1);
            lectureContentsFileList.add(lectureContentsFile);
        }

        model.addAttribute("lecture",lecture);
        model.addAttribute("lectureDivide",lectureDivide.get(0));
        model.addAttribute("lectureContentsList",lectureContents);
        model.addAttribute("lectureContentsFileList",lectureContentsFileList);
        return "/lecture/lectureDetail";
    }

    @GetMapping(value = "/lecture/lectureEdit/{id}")
    public String goLectureEdit(@PathVariable("id") Long id,Model model){
        Lecture lecture = lectureService.getLectureById(id);
        List<LectureDivide> lectureDivide = lectureDivideService.getLectureDivideByLecture(lecture);
        List<LectureContents> lectureContents = lectureContentsService.getLectureContentsList(lectureDivide.get(0));
        List<LectureContentsFile> lectureContentsFileList = new ArrayList<>();
        for (LectureContents lectureContents1 : lectureContents){
            LectureContentsFile lectureContentsFile = lectureContentsService.getLectureContentsFileByLectureContents(lectureContents1);
            lectureContentsFileList.add(lectureContentsFile);
        }

        model.addAttribute("lecture",lecture);
        model.addAttribute("lectureDivide",lectureDivide.get(0));
        model.addAttribute("lectureContentsList",lectureContents);
        model.addAttribute("lectureContentsFileList",lectureContentsFileList);
        return "lecture/lectureEdit";
    }
}