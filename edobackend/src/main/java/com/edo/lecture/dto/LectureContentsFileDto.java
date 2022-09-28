package com.edo.lecture.dto;

import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureContentsFile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LectureContentsFileDto {
    String uuidPath;



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
