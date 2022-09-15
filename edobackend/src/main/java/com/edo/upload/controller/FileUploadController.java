package com.edo.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/upload")
public class FileUploadController {
    @PostMapping(consumes = "multipart/form-data")
    public void upload(@RequestParam("file") @RequestPart(value="file",required = false) List<MultipartFile> files)
            throws Exception {
        for(MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String fileType = file.getContentType();

            File dest = new File("C:/WorkSpace/FileUploader/images/"+originalFileName);
            file.transferTo(dest);
            log.info("uploaded file " + file.getOriginalFilename());
        }
    }
}
