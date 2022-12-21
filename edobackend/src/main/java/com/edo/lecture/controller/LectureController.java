package com.edo.lecture.controller;

import com.edo.lecture.dto.*;
import com.edo.lecture.entity.*;
import com.edo.lecture.repository.LectureDivideRepository;
import com.edo.lecture.repository.LectureRepository;
import com.edo.lecture.repository.LectureSubscribeRepository;
import com.edo.lecture.service.LectureDivideService;
import com.edo.lecture.service.LectureService;
import com.edo.lecture.service.LectureContentsService;
import com.edo.user.entity.Member;
import com.edo.user.repository.MemberRepository;
import com.edo.util.file.FileVO;
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
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    MemberRepository memberRepository;
    @Autowired
    LectureContentsService lectureContentsService;
    @Autowired
    LectureDivideRepository lectureDivideRepository;

    @Autowired
    LectureSubscribeRepository lectureSubscribeRepository;
    @Autowired
    LectureRepository lectureRepository;

    /***
     *
     * @param part : 강의 분야 ex) 전체,파이썬,코딩테스트 등
     * @param pageNumber : 현재 화면에 보이는 페이지 부분
     * @param size : 한 페이지 당 몇 개의 요소를 뽑아 올 건지 ,default가 12이기 때문에 한 페이지 당 12개씩 보여줌
     * @param model
     * @return
     */
    @GetMapping(value = "/lecture/{part}")
    public String Lecture
            (@PathVariable(value = "part", required = false) String part,
             @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
             @RequestParam(value = "size", required = false, defaultValue = "12") int size,
             Model model , Principal principal) {
        try{
        String email = principal.getName();
            Long id = memberRepository.findMemberIdByMemberEmail(email);
            model.addAttribute("userId",id);
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@????"+id);
        }catch (Exception e){}

        model.addAttribute("partPage", lectureService.getPageByPart(pageNumber, size,part));
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
     * @param lectureAddDto LectureAddDto는 LectureAdd에서 가져온 정보를 Lecture Entity의 모습으로 변경한 후
     *                      저장하기 위해 사용된다.
     * @param model
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/lecture/add", consumes = "multipart/form-data")
    public String LectureAdd(@ModelAttribute LectureAddDto lectureAddDto, Model model,Principal principal) throws IOException,
            NullPointerException {
        String email = principal.getName();
        Member member = memberRepository.findByMemberEmail(email).get();
        //운영체제가 윈도우인지 , 리눅스인지에 따라 루트 변경
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

                //파일저장
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
            lectureAddDto.setMember(member);


            Lecture lecture = lectureService.lectureAdd(lectureAddDto);
            model.addAttribute("lectureId", lecture.getId());
            if (lectureAddDto.getThisId() == null) {
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
        if (!lectureContentsAddDto.getDivideId().equals("")) {
            lectureDivideDto.setId(Long.parseLong(lectureContentsAddDto.getDivideId()));
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@>>>>>>>>>>>>>>>>>>>DivideId추가"+lectureContentsAddDto.getDivideId());
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
    //강의 차시 업로드(파일부분)
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


    //강의 상세페이지
    @GetMapping(value = "/lecture/lectureDetail/{id}")
    public String goDetail(@PathVariable("id") Long id, Model model,Principal principal) {
        Lecture lecture = lectureService.getLectureById(id);
        int heart = 0;
        int listen = 0;
        try {
            String email = principal.getName();
            Optional<Member> member= memberRepository.findByMemberEmail(email);
            Member member1 = member.get();
            int like = lectureSubscribeRepository.searchHeartByLectureAndMember(member1.getMemberId(),id);
            heart = like;
        }
        catch (Exception e){

        }
        try {
            String email = principal.getName();
            Optional<Member> member= memberRepository.findByMemberEmail(email);
            Member member1 = member.get();
            int listento = lectureSubscribeRepository.searchListenByLectureAndMember(member1.getMemberId(),id);
            listen = listento;
        }
        catch (Exception e){

        }
        try {
            List<LectureDivide> lectureDivide = lectureDivideService.getListDivide(lecture);
            model.addAttribute("lecture", lecture);
            model.addAttribute("lectureDivide", lectureDivide);
            model.addAttribute("heart",heart);
            model.addAttribute("listen",listen);
            return "lecture/lectureDetail";
        } catch (IndexOutOfBoundsException e) {
            model.addAttribute("heart",heart);
            model.addAttribute("lecture", lecture);
            model.addAttribute("listen",listen);
            return "lecture/lectureDetail";
        }
    }

    //강의수정
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
    //강의 차시 추가
    @GetMapping(value = "/lecture/divideAdd/{id}")
    public String addDivide(@PathVariable("id") Long id, Model model) {
        LectureDivide lectureDivide = lectureDivideService.addDivide(id);
        List<Long> lectureDivideIds = lectureDivideService.getLectureDivideIds(id);

        model.addAttribute("lectureId", id);
        model.addAttribute("firstDivideId", lectureDivide.getId());
        model.addAttribute("lectureDivideIds", lectureDivideIds);
        model.addAttribute("lectureDivide", lectureDivide);

        return "lecture/lectureContentsEdit";
    }

    //강의 차시파일 삭제
    @GetMapping(value = "/lecture/contentsdelete/{id}")
    public String deleteDivide(@PathVariable("id") Long id, Model model) {
        Lecture lecture = lectureDivideService.getLecture(id);
        lectureDivideService.deleteDivide(id);
        List<Long> lectureDivideIds = lectureDivideService.getLectureDivideIds(lecture.getId());
        LectureDivide lectureDivide = lectureDivideService.getLectureDivideFirstByLecture(lecture);
        model.addAttribute("lectureId", lecture.getId());
        model.addAttribute("firstDivideId", lectureDivide.getId());
        model.addAttribute("lectureDivideIds", lectureDivideIds);
        model.addAttribute("lectureDivide", lectureDivide);
        return "lecture/lectureContentsEdit";
    }

    //강의 삭제
    @GetMapping(value = "/lecture/lectureRemove/{id}/{part}/{pageNumber}")
    public String deleteLecture
            (       @PathVariable(value = "id",required = false) Long id,
                    @PathVariable(value = "part", required = false) String part,
                    @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                    @RequestParam(value = "size", required = false, defaultValue = "12") int size,
                    Model model,Principal principal) {
        String email = principal.getName();
        Member member = memberRepository.findByMemberEmail(email).get();
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
        model.addAttribute("userId",member.getMemberId());
        model.addAttribute("posts", lectureService.getPage(pageNumber, size));
        model.addAttribute("partPage", lectureService.getPageByPart(pageNumber, size,part));
        model.addAttribute("list", lectureList);
        model.addAttribute("part", part);
        return "lecture/lecture";
    }



    //강의 좋아요 버튼 클릭 시
    @PostMapping("/lecture/lectureHeart")
    public String comheart(heartAndSubData data,Principal principal,Model model) {
        //세션을 통해 현재 유저 정보 가져옴
        String email = principal.getName();
        int heart = data.getHeart();
        Long id = memberRepository.findMemberIdByMemberEmail(email);
        Lecture lecture = lectureRepository.findLectureById(data.getId());
        Optional<Member> member = memberRepository.findByMemberEmail(email);
        Member member1 = member.get();
        try {
           Long catchId = lectureService.findlikeId(id, data.getId());
            log.info("#############################################id값 있음!!"+id+data.getId());
            LectureMemberDto lectureMemberDto = new LectureMemberDto();
            lectureMemberDto.setMember(member1);
            lectureMemberDto.setId(catchId);
            lectureMemberDto.setHeart(data.getHeart());
            lectureMemberDto.setLecture(lecture);
            lectureMemberDto.setSubscribe(0);
            LectureMember lectureMember = lectureMemberDto.toEntity(lectureMemberDto);
            log.info("#############################################id값 있음!!"+catchId);
            lectureSubscribeRepository.save(lectureMember);
        }catch (Exception e){
            LectureMemberDto lectureMemberDto = new LectureMemberDto();
            lectureMemberDto.setMember(member1);
            lectureMemberDto.setHeart(data.getHeart());
            lectureMemberDto.setLecture(lecture);
            lectureMemberDto.setSubscribe(0);
            LectureMember lectureMember = lectureMemberDto.toEntity(lectureMemberDto);
            log.info("###########################################id값 없음!!");
            lectureSubscribeRepository.save(lectureMember);
        }
        try {
            List<LectureDivide> lectureDivide = lectureDivideService.getListDivide(lecture);
            model.addAttribute("lecture", lecture);
            model.addAttribute("lectureDivide", lectureDivide);
            model.addAttribute("heart",heart);
            return "lecture/lectureDetail";
        } catch (IndexOutOfBoundsException e) {
            model.addAttribute("lecture", lecture);
            model.addAttribute("heart",heart);
            return "lecture/lectureDetail";
        }
    }

    //강의수강 클릭 시
    @GetMapping(value = "/lecture/listen/{id}")
    public String lectureListen(@PathVariable("id") Long LectureId,Principal principal, Model model) {
        String email = principal.getName();
        Member member = memberRepository.findByMemberEmail(email).get();
        Lecture lecture = lectureRepository.findLectureById(LectureId);
        LectureMember lectureMember1 = new LectureMember();
        try {
            LectureMember lectureMember = lectureSubscribeRepository.findByMemberAndLecture(member, lecture);
            lectureMember.setListen(1);
            lectureMember1 = lectureService.saveLectureListen(lectureMember);
        }catch(Exception e){
            LectureMemberDto lectureMemberDto = new LectureMemberDto();
            lectureMemberDto.setListen(1);
            lectureMemberDto.setHeart(0);
            lectureMemberDto.setSubscribe(0);
            lectureMemberDto.setMember(memberRepository.findByMemberEmail(email).get());
            lectureMemberDto.setLecture(lectureRepository.findLectureById(LectureId));
            LectureMember lectureMember = lectureMemberDto.toEntity(lectureMemberDto);
            lectureMember1 = lectureService.saveLectureListen(lectureMember);
        }
        try {
            List<LectureDivide> lectureDivide = lectureDivideService.getListDivide(lectureRepository.findLectureById(LectureId));
            model.addAttribute("lecture", lectureRepository.findLectureById(LectureId));
            model.addAttribute("lectureDivide", lectureDivide);
            model.addAttribute("heart",lectureMember1.getHeart());
            model.addAttribute("listen",lectureMember1.getListen());
            return "lecture/lectureDetail";
        } catch (IndexOutOfBoundsException e) {
            model.addAttribute("heart",lectureMember1.getHeart());
            model.addAttribute("lecture", lectureRepository.findLectureById(LectureId));
            model.addAttribute("listen",lectureMember1.getListen());
            return "lecture/lectureDetail";
        }
    }
}