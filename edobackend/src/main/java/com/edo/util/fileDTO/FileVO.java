package com.edo.util.fileDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileVO {
    private MultipartFile lectureContentsFile1;
    private MultipartFile lectureContentsFile2;
    private MultipartFile lectureContentsFile3;
    private MultipartFile lectureContentsFile4;
    private MultipartFile lectureContentsFile5;
    //JsonProperty를 사용하는 이유는 hidden type의 경우 json 형태로 날라오기 때문에 타입을 String으로 변경하기 위해 사용
    @JsonProperty("ContentsSeq1")
    private String ContentsSeq1;
    @JsonProperty("ContentsSeq2")
    private String ContentsSeq2;
    @JsonProperty("lectureTitle")
    private String lectureTitle;
    @JsonProperty("ContentsId")
    private String ContentsId;

}
