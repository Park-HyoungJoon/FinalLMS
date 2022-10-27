package com.edo.lecture.dto;

import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureContentsFile;
import com.edo.lecture.entity.LectureDivide;
import com.edo.lecture.service.LectureDivideService;
import com.edo.lecture.service.LectureService;
import com.edo.util.ApplicationContext.BeanUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LectureDivideAndContentsDto {
    @Autowired
    LectureDivideService lectureDivideService;

    @Autowired
    LectureService lectureService;
    /**
     * LectureDivide
     */
    private Long id;
    private Long lectureId;
    private String strLectureId;
    private String lectureDivideTitle;
    private Lecture lecture;

    @JsonProperty("divideId")
    private String divideId;
    //차시순번
    private int lectureDivideSeq;

    /**
     * LectureContents And LectureContentsFile
     */
    LectureDivide lectureDivide;
    String lectureContentsTitle;
    String lectureContentsInfo;
    String uuidPath;
    @JsonProperty("lectureDivideInfo")
    private String lectureDivideInfo;
    @JsonProperty("ListLectureContentsTitle")
    private String[] ListLectureContentsTitle;
    @JsonProperty("contentsIds")
    private String[] contentsIds;



    //Divide 차시 순번 가져오기
    public int SearchSeq(){
        lectureDivideService = BeanUtils.getBean(LectureDivideService.class);
        int selectSeq = lectureDivideService.selectSeq();
        return selectSeq;
    }
    private static ModelMapper modelMapper = new ModelMapper();

    /***
     * lectureDivideDto에 저장된 정보를 LectureDivide Entity로 변환 
     * @param lectureDivideDto
     * @return lectureDivide
     */
    public LectureDivide dtoToLectureDivide(LectureDivideAndContentsDto lectureDivideDto) {
        int MaxSeq = SearchSeq();
        lectureService = BeanUtils.getBean(LectureService.class);
        Lecture lecture1 = lectureService.getLectureById(lectureDivideDto.getLectureId());
        this.setLecture(lecture1);
        this.setLectureDivideSeq(MaxSeq);
        this.setLectureDivideInfo(lectureDivideDto.getLectureDivideInfo());
        return modelMapper.map(this,LectureDivide.class);}

    /***
     *  lectureDivde Entity를 LectureDivideDto로 변환
     * @param lectureDivide
     * @return lectureDivideDto
     */
    public static LectureDivideAndContentsDto of (LectureDivide lectureDivide){
        return modelMapper.map(lectureDivide,LectureDivideAndContentsDto.class);
    }
    //현재 Dto를 LectureContents 로 변환
    public LectureContents dtoToLectureContents() { return modelMapper.map(this,LectureContents.class);}

    //LectureContents에 대한 정보를 Dto에 주입
    public static LectureDivideAndContentsDto of (LectureContents lectureContents){
        return modelMapper.map(lectureContents,LectureDivideAndContentsDto.class);
    }
    public LectureContentsFile toLectureContentsFile(MultipartFile file, LectureContents lectureContents){
        if(file==null){
            return null;
        }
        //파일 이름 file.getOriginalFilename();
        //파일 컨텐츠 타입file.getContentType();
        //파일 위치 file.getResource()
        LectureContentsFile lectureContentsFile = new LectureContentsFile();
        lectureContentsFile.setFileName(this.uuidPath);
        lectureContentsFile.setLectureContents(lectureContents);
        lectureContentsFile.setFileLoc(String.valueOf(file.getResource()));
        lectureContentsFile.setFileSize(file.getSize());
        lectureContentsFile.setFileType(file.getContentType());
        return lectureContentsFile;
    }
}
