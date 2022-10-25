package com.edo.util.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FileVO {
    private MultipartFile lectureContentsFile1;
    private MultipartFile lectureContentsFile2;
    private MultipartFile lectureContentsFile3;
    private MultipartFile lectureContentsFile4;
    private MultipartFile lectureContentsFile5;

    private String lectureContentsLink1;
    private String lectureContentsLink2;
    private String lectureContentsLink3;
    private String lectureContentsLink4;
    private String lectureContentsLink5;
    //JsonProperty를 사용하는 이유는 hidden type의 경우 json 형태로 날라오기 때문에 타입을 String으로 변경하기 위해 사용
    @JsonProperty("ContentsSeq")
    private String ContentsSeq;
    public List<MultipartFile> ContentsFileToList(FileVO fileVO){
        List<MultipartFile> list = new ArrayList<>();

        list.add(fileVO.getLectureContentsFile1());
        list.add(fileVO.getLectureContentsFile2());
        list.add(fileVO.getLectureContentsFile3());
        list.add(fileVO.getLectureContentsFile4());
        list.add(fileVO.getLectureContentsFile5());
        return list;
    }
    public List<String> ContentsLinkToList(FileVO fileVO){
        List<String> list = new ArrayList<>();
        list.add(fileVO.getLectureContentsLink1());
        list.add(fileVO.getLectureContentsLink2());
        list.add(fileVO.getLectureContentsLink3());
        list.add(fileVO.getLectureContentsLink4());
        list.add(fileVO.getLectureContentsLink5());
        return list;
    }
}
