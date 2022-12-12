package com.edo.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {
    public String uploadFile(String uploadPath, String oriFilename, byte[] fileData) throws IOException {

        UUID uuid = UUID.randomUUID();

        String extension = oriFilename.substring(oriFilename.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;

        String fileUploadUrl  = uploadPath + "/" + savedFileName;

        FileOutputStream fos = new FileOutputStream(fileUploadUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }
}
