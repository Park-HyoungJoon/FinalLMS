package com.edo.lecture.dto;

import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureContentsFile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/***
 * Contents를 수정할 때 List<Contents> , List<ContentsFile>의 정보를 담고 있다.
 */
@Getter
@Setter
public class ContentsEditDto {
    List<LectureContents> lectureContentsList;
    List<LectureContentsFile> lectureContentsFileList;

    List<MultipartFile> lectureContentsFiles;
    List<String> lectureContentsLink;
    @JsonProperty("ListLectureContentsTitle")
    private String[] ListLectureContentsTitle;
    @JsonProperty("ListLectureContentsInfo")
    private String[] ListLectureContentsInfo;

}
