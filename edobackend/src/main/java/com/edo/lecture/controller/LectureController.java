package com.edo.lecture.controller;

import com.edo.lecture.dto.*;
import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureContentsFile;
import com.edo.lecture.entity.LectureDivide;
import com.edo.lecture.repository.LectureDivideRepository;
import com.edo.lecture.repository.LectureRepository;
import com.edo.lecture.service.LectureDivideService;
import com.edo.lecture.service.LectureService;
import com.edo.lecture.service.LectureContentsService;
import com.edo.util.file.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    String imgPath;
    String videoPath;
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
    LectureDivideRepository lectureDivideRepository;

    @Autowired
    LectureRepository lectureRepository;

    @GetMapping(value = "/lecture/{part}")
    public String Lecture
            (@PathVariable(value = "part", required = false) String part,
             @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
             @RequestParam(value = "size", required = false, defaultValue = "12") int size,
             Model model) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String path = auth.getName();
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ id : : : ", path);
        List<Lecture> lectureList = lectureRepository.findAll();
        model.addAttribute("posts", lectureService.getPage(pageNumber, size));
        model.addAttribute("partPage", lectureService.getPageByPart(pageNumber, size,part));
        model.addAttribute("list", lectureList);
        model.addAttribute("part", part);
        return "lecture/lecture";
    }


    @GetMapping(value = "/lecture/add")
    public String LectureAdd2(Model model) {
        model.addAttribute("lectureAddDto", new LectureAddDto());
        return "lecture/lectureAdd";
    }

    /**
     * lectureAdd.html에서 강의 추가 정보를 가져온 후 강의 차시로 넘어가는 객체다.
     *
     * @param lectureAddDto LectureAddDto는 LectureAdd에서 가져온 정보를 Lecture Entity의 모습으로 변경한 후
     *                      저장하기 위해 사용된다.
     * @param model
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/lecture/add", consumes = "multipart/form-data")
    public String LectureAdd(@ModelAttribute LectureAddDto lectureAddDto, Model model) throws IOException,
            NullPointerException {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            imgPath =System.getProperty("user.dir") + imgRoot;
            videoPath = fileRoot;
        }else{
            imgPath = "/home/phj/image/";
            videoPath = "/home/phj/video/";
        }
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
        /***
         * 강좌추가가 아닌 강좌수정으로 들어온 경우 기존에 있던 강좌 이미지 제거
         */
        try{
            Long id = Long.parseLong(lectureAddDto.getThisId());
            String fileName = lectureService.getLectureImgeById(id);
            File existFile = new File(imgPath,fileName);
            if(existFile.exists()){
                existFile.delete();
            }
        }catch (Exception e){

        }


        /**
         * resourcePath는 현재 프로젝트의 상대경로를 가져와 그 안에
         * resources폴더까지의 경로이다.
         *
         * file
         */
        try {
            String uuid = UUID.randomUUID().toString();
            String uuidFileName = uuid + "_" + lectureAddDto.getRealLectureImg().getOriginalFilename();
            MultipartFile multipartFile = lectureAddDto.getRealLectureImg();
            File saveFile = new File(imgPath,uuidFileName);
            lectureAddDto.setLectureImage(uuidFileName);
            try{
                multipartFile.transferTo(saveFile);
            }catch (Exception e){
                log.error(e.getMessage());
            }

             } catch (Exception e) {
        } finally {
            //강좌 생성 내용
            //Lecture 테이블에 LecutreImg 필드에 해당 이미지의 경로를 넣기 위해 setLectureImg를 통해 저장
            if (lectureAddDto.getThisId() != null) {
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
            model.addAttribute("lectureId", lecture.getId());
            if (lectureAddDto.getThisId() == null) {
                Long id = lecture.getId();
                LectureDivide lectureDivide = lectureDivideService.insertFirstDivide(id);
                LectureDivide lectureDivide2 = lectureDivideService.save(lectureDivide);
                return "lecture/lectureContentsEdit";
            } else {
                Lecture lecture1 = lectureService.getLectureById(Long.parseLong(lectureAddDto.getThisId()));
                List<Long> lectureDivideIds = lectureDivideService.getLectureDivideIds(lecture1.getId());
                try {
                    LectureDivide lectureDivide = lectureDivideService.getLectureDivideFirstByLecture(lecture1);
                    List<LectureContents> lectureContentsList = lectureContentsService.getLectureContentsList(lectureDivide);
                    List<LectureContentsFile> lectureContentsFileList = new ArrayList<>();
                    for (LectureContents lectureContents : lectureContentsList) {
                        LectureContentsFile lectureContentsFile = lectureContentsService.getLectureContentsFileByLectureContents(lectureContents);
                        lectureContentsFileList.add(lectureContentsFile);
                    }
                    model.addAttribute("firstDivideId", lectureDivideIds.get(0));
                    model.addAttribute("lectureDivideIds", lectureDivideIds);
                    model.addAttribute("lectureDivide", lectureDivide);
                    model.addAttribute("lectureContentsList", lectureContentsList);
                    model.addAttribute("lectureContentsFileList", lectureContentsFileList);
                    return "lecture/lectureContentsEdit";
                } catch (IndexOutOfBoundsException e) {
                    return "lecture/lectureContentsEdit";
                }
            }
        }
    }

    @PostMapping(value = "lecture/divide/edit/{id}")
    public String LectureContentsPartEdit(@PathVariable("id") String divideId, Model model) {
        model.addAttribute("contentsEditDto", new ContentsEditDto());
        return "lecture/lectureContentsEdit";
    }

    /***
     * 강의 시청 페이지 호출
     * @param model
     * @return
     */
    @CrossOrigin
    @GetMapping(value = "/lecture/contents/{id}/{lectureDivideId}")
    public String LectureContents(@PathVariable("id") Long lectureId, @PathVariable("lectureDivideId") Long lectureDivideId, Model model) {
        List<Long> lectureDivideIds = lectureDivideService.getLectureDivideIds(lectureId);
        LectureDivide lectureDivide = lectureDivideService.getLectureDivideById(lectureDivideId);
        List<LectureContents> lectureContentsList = lectureContentsService.getLectureContentsList(lectureDivide);
        List<LectureContentsFile> lectureContentsFileList = new ArrayList<>();
        for (LectureContents lectureContents : lectureContentsList) {
            log.info(lectureContents.getId());
            LectureContentsFile lectureContentsFile = lectureContentsService.getLectureContentsFileByLectureContents(lectureContents);
            lectureContentsFileList.add(lectureContentsFile);
        }
        model.addAttribute("lectureId", lectureId);
        model.addAttribute("lectureDivideIds", lectureDivideIds);
        model.addAttribute("lectureDivide", lectureDivide);
        model.addAttribute("lectureContentsList", lectureContentsList);
        model.addAttribute("lectureContentsFileList", lectureContentsFileList);
        return "lecture/lectureContents";
    }

    @GetMapping("/lecture/contentsEdit/{id}")
    public String LectureContentsEditGetId(@PathVariable("id") Long divideId, Model model) {
        Lecture lecture = lectureDivideService.getLecture(divideId);
        List<Long> lectureDivideIds = lectureDivideService.getLectureDivideIds(lecture.getId());
        LectureDivide lectureDivide = lectureDivideService.getLectureDivideById(divideId);
        List<LectureContents> lectureContentsList = lectureContentsService.getLectureContentsList(lectureDivide);
        List<LectureContentsFile> lectureContentsFileList = new ArrayList<>();
        for (int i = 0; i < lectureContentsList.size(); i++) {
            LectureContents lectureContents = lectureContentsList.get(i);
            lectureContentsFileList.add(lectureContentsService.getLectureContentsFileByLectureContents(lectureContents));
        }
        log.info("#####################lectureId : ::  ::  :: : " + lecture.getId());
        model.addAttribute("lectureId", lecture.getId());
        model.addAttribute("lectureDivideIds", lectureDivideIds);
        model.addAttribute("lectureDivide", lectureDivide);
        model.addAttribute("firstDivideId", lectureDivide.getId());
        model.addAttribute("lectureContentsList", lectureContentsList);
        model.addAttribute("lectureContentsFileList", lectureContentsFileList);

        return "lecture/lectureContentsEdit";
    }

    @PostMapping(value = "/lecture/contents")
    public String LectureDivideAndContentsAdd(@RequestBody LectureDivideAndContentsDto lectureContentsAddDto, Model model) {
        Long lectureId = Long.parseLong(lectureContentsAddDto.getStrLectureId());
        LectureDivideAndContentsDto lectureDivideDto = new LectureDivideAndContentsDto();
        lectureDivideDto.setLectureId(lectureId);
        if (lectureContentsAddDto.getDivideId() != null) {
            lectureDivideDto.setId(Long.parseLong(lectureContentsAddDto.getDivideId()));
        }
        lectureDivideDto.setLectureDivideTitle(lectureContentsAddDto.getLectureDivideTitle());
        lectureDivideDto.setLectureDivideInfo(lectureContentsAddDto.getLectureDivideInfo());
        LectureDivideAndContentsDto lectureContentsDto = new LectureDivideAndContentsDto();
        LectureDivide lectureDivide = lectureDivideDto.dtoToLectureDivide(lectureDivideDto);
        LectureDivide getLectureDivide = lectureDivideService.save(lectureDivide);
        List<Long> ContentsId = null;
        if (lectureContentsService.getLectureContentsList(getLectureDivide) != null) {
            ContentsId = lectureContentsService.getContentsIdsByDivideId(getLectureDivide.getId());
            for (int i = 0; i < ContentsId.size(); i++) {
                lectureContentsService.removeAll(ContentsId);
            }
        }
        List<LectureContents> lectureContents3 = new ArrayList<>();
        for (int i = 0; i < lectureContentsAddDto.getListLectureContentsTitle().length; i++) {
            String lectureContentsTitle = lectureContentsAddDto.getListLectureContentsTitle()[i];
            lectureContentsDto.setLectureDivide(getLectureDivide);
            lectureContentsDto.setLectureContentsTitle(lectureContentsTitle);
            LectureContents lectureContents = lectureContentsDto.dtoToLectureContents();
            lectureContents3.add(lectureContentsService.save(lectureContents));
        }
        lectureContents3.size();
        List<GetIdDto> getId = new ArrayList<>();
        GetIdDto getIdDto = new GetIdDto();
        for (int i = 0; i < lectureContents3.size(); i++) {
            getIdDto.setGetId(lectureContents3.get(i).getId());
            getId.add(getIdDto);
        }
        model.addAttribute("getSize", lectureContents3.size());
        model.addAttribute("getId", getId);
        return "lecture/lectureContentsEdit";
    }


    /***
     * lectureContents.html에서 form으로 파일 데이터를 받아 LectureContentsFile Entity에 저장해놓는 컨트롤러이다.
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
    @PostMapping(value = "/contents/uploader", consumes = "multipart/form-data")
    public String ContentsFileCreate(FileVO fileVO,Model model)
            throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            imgPath =System.getProperty("user.dir") + imgRoot;
            videoPath = fileRoot;
        }else{
            imgPath = "/home/phj/image/";
            videoPath = "/home/phj/video/";
        }

        /***
         * 기존 이미 저장되어있는 정보 불러오는 부분
         * 첫 번째 강의ID 값 구해온 후 ->
         */
        try{
            //현재 DivideId값
            Long DivideId = Long.parseLong(fileVO.getFirstDivideId());
            LectureDivide lectureDivide = lectureDivideService.getLectureDivideById(DivideId);
            List<LectureContents> lectureContentsList2= lectureContentsService.getLectureContentsList(lectureDivide);
            for (LectureContents lectureContents : lectureContentsList2){
                if(lectureContentsService.getLectureContentsFileByLectureContents(lectureContents).getId() == null)continue;
                LectureContentsFile lectureContentsFile = lectureContentsService.getLectureContentsFileByLectureContents(lectureContents);
                File file = new File(videoPath,lectureContentsFile.getFileName());
                if(file.exists()){
                    file.delete();
                }
            }}catch (Exception e){

        }

        Long newContents = lectureContentsService.getNewContents();
        int idx = Integer.parseInt(fileVO.getContentsSeq());
        List<LectureContents> lectureContentsList = fileVO.ContentsToList(fileVO);
        /***
         * 만약 차시 수가 더 줄어든 경우 오버된 차시 삭제
         * lectureContents , lectureContentsFile DB내용 삭제 + 삭제된 DB 내용과 연관된 파일 삭제
         */
        List<MultipartFile> files = fileVO.ContentsFileToList(fileVO);
        for (int i = 1; i <= idx; i++) {
            if (files.get(i - 1) == null) {
                continue;
            }
            LectureContentsFile lectureContentsFile = lectureContentsService.getLectureContentsFileByLectureContents(lectureContentsList.get(i - 1));
            try {
                File file = new File(videoPath, lectureContentsFile.getFileName());
                file.delete();
            } catch (NullPointerException e) {

            }
            String uuid = UUID.randomUUID().toString();
            String uuidFileName = uuid + "_" + files.get(i - 1).getOriginalFilename();
            //uuid를 통한 랜덤값 지정
            LectureDivideAndContentsDto lectureContentsFileDto = new LectureDivideAndContentsDto();
            lectureContentsFileDto.setUuidPath(uuidFileName);
            try {
                MultipartFile multipartFile = files.get(i - 1);
                File saveFile = new File(videoPath, uuidFileName);
                try {
                    multipartFile.transferTo(saveFile);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            } catch (Exception e) {
            }
            try {
                LectureContentsFile lectureContentsfile = lectureContentsFileDto.toLectureContentsFile(
                        files.get(i - 1), lectureContentsService.getContentsById(newContents - (idx - i)), lectureContentsFile.getId()
                );
                lectureContentsService.save(lectureContentsfile);
            } catch (Exception e) {
                LectureContentsFile lectureContentsfile = lectureContentsFileDto.toLectureContentsFile(
                        files.get(i - 1), lectureContentsService.getContentsById(newContents - (idx - i)), null
                );
                lectureContentsService.save(lectureContentsfile);
            }
        }
        List<Lecture> lectureList = lectureRepository.findAll();
        model.addAttribute("posts", lectureService.getPage(1, 12));
        model.addAttribute("partPage", lectureService.getPageByPart(1, 12,"All"));
        model.addAttribute("list", lectureList);
        model.addAttribute("part","All");
        return "lecture/lecture";
    }


    @GetMapping(value = "/lecture/lectureDetail/{id}")
    public String goDetail(@PathVariable("id") Long id, Model model) {
        Lecture lecture = lectureService.getLectureById(id);
        try {
            List<LectureDivide> lectureDivide = lectureDivideService.getListDivide(lecture);
            model.addAttribute("lecture", lecture);
            model.addAttribute("lectureDivide", lectureDivide);
            return "lecture/lectureDetail";
        } catch (IndexOutOfBoundsException e) {
            model.addAttribute("lecture", lecture);
            return "lecture/lectureDetail";
        }
    }

    @GetMapping(value = "/lecture/lectureEdit/{id}")
    public String goLectureEdit(@PathVariable("id") Long id, Model model) {
        Lecture lecture = lectureService.getLectureById(id);
        try {
            LectureDivide lectureDivide = lectureDivideService.getLectureDivideFirstByLecture(lecture);
            List<LectureContents> lectureContents = lectureContentsService.getLectureContentsList(lectureDivide);
            List<LectureContentsFile> lectureContentsFileList = new ArrayList<>();
            for (LectureContents lectureContents1 : lectureContents) {
                LectureContentsFile lectureContentsFile = lectureContentsService.getLectureContentsFileByLectureContents(lectureContents1);
                lectureContentsFileList.add(lectureContentsFile);
            }

            model.addAttribute("lecture", lecture);
            model.addAttribute("lectureDivide", lectureDivide);
            model.addAttribute("lectureContentsList", lectureContents);
            model.addAttribute("lectureContentsFileList", lectureContentsFileList);
            return "lecture/lectureEdit";
        } catch (IndexOutOfBoundsException e) {
            model.addAttribute("lecture", lecture);
            return "lecture/lectureEdit";
        }
    }


    /***
     *  LectureId값을 받아 LectureDivide를 하나 더 추가하는 함수 lectureContetnsEdit에서 사용
     * @param id  == LectureId값
     * @param model
     * @param model
     * @return
     */
    @GetMapping(value = "/lecture/divideAdd/{id}")
    public String addDivide(@PathVariable("id") Long id, Model model) {
        LectureDivide lectureDivide = lectureDivideService.addDivide(id);
        List<Long> lectureDivideIds = lectureDivideService.getLectureDivideIds(id);

        model.addAttribute("lectureId", id);
        model.addAttribute("lectureDivideIds", lectureDivideIds);
        model.addAttribute("lectureDivide", lectureDivide);

        return "lecture/lectureContentsEdit";
    }

    @GetMapping(value = "/lecture/contentsdelete/{id}")
    public String deleteDivide(@PathVariable("id") Long id, Model model) {
        Lecture lecture = lectureDivideService.getLecture(id);
        lectureDivideService.deleteDivide(id);
        List<Long> lectureDivideIds = lectureDivideService.getLectureDivideIds(lecture.getId());
        LectureDivide lectureDivide = lectureDivideService.getLectureDivideFirstByLecture(lecture);
        model.addAttribute("lectureId", lecture.getId());
        model.addAttribute("lectureDivideIds", lectureDivideIds);
        model.addAttribute("lectureDivide", lectureDivide);
        return "lecture/lectureContentsEdit";
    }

    @GetMapping(value = "/lecture/lectureRemove/{id}/{part}/{pageNumber}")
    public String deleteLecture
            (       @PathVariable(value = "id",required = false) Long id,
                    @PathVariable(value = "part", required = false) String part,
                    @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                    @RequestParam(value = "size", required = false, defaultValue = "12") int size,
                    Model model) {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            imgPath =System.getProperty("user.dir") + imgRoot;
            videoPath = fileRoot;
        }else{
            imgPath = "/home/phj/image/";
            videoPath = "/home/phj/video/";
        }

        /***
         * 강좌추가가 아닌 강좌수정으로 들어온 경우 기존에 있던 강좌 이미지 제거
         */
        try{
            String fileName = lectureService.getLectureImgeById(id);
            File existFile = new File(imgPath,fileName);
            if(existFile.exists()){
                existFile.delete();
            }
        }catch (Exception e){

        }
        try{
            Lecture lecture = lectureService.getLectureById(id);
            List<LectureDivide> lectureDivideList = lectureDivideService.getListDivide(lecture);
            for(LectureDivide lectureDivide : lectureDivideList){
                List<LectureContents> lectureContentsList = lectureContentsService.getLectureContentsList(lectureDivide);
                for(LectureContents lectureContents : lectureContentsList){
                    LectureContentsFile lectureContentsFile = lectureContentsService.getLectureContentsFileByLectureContents(lectureContents);
                    File existFile = new File(videoPath,lectureContentsFile.getFileName());
                    if(existFile.exists()){
                        existFile.delete();
                    }
                }
            }
        }catch (Exception e){

        }
        lectureRepository.deleteLectureById(id);
        List<Lecture> lectureList = lectureRepository.findAll();
        model.addAttribute("posts", lectureService.getPage(pageNumber, size));
        model.addAttribute("partPage", lectureService.getPageByPart(pageNumber, size,part));
        model.addAttribute("list", lectureList);
        model.addAttribute("part", part);
        return "lecture/lecture";
    }

}