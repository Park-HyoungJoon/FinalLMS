//package com.edo.community.controller;
//
//import com.edo.community.dto.CommunityDto;
//import com.edo.user.dto.UserRequestDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Date;
//import java.util.Random;
//
//@CrossOrigin
//@Controller
//@RequiredArgsConstructor
//public class CommunityController {
//
//    @PostMapping("/community/upload")
//    public ResponseEntity<Object> uploadCommunity(@RequestBody CommunityDto communityDto){
//        String UPLOAD_PATH ="C:\\myUpload";
//
//        try{
//            MultipartFile[] multipartFiles = communityDto.getFiles();
//            for(int i=0; i<multipartFiles.length; i++){
//                MultipartFile file = multipartFiles[i];
//
//                String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
//                String originName = file.getOriginalFilename(); // ex) 파일.jpg
//                String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
//                originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
//                long fileSize = file.getSize(); // 파일 사이즈
//
//                File fileSave = new File(UPLOAD_PATH, fileId + "." + fileExtension); // ex) fileId.jpg
//                if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
//                    fileSave.mkdirs();
//                }
//
//                file.transferTo(fileSave); // fileSave의 형태로 파일 저장
//
//                System.out.println("fileId= " + fileId);
//                System.out.println("originName= " + originName);
//                System.out.println("fileExtension= " + fileExtension);
//                System.out.println("fileSize= " + fileSize);
//
//
//            }
//
//        }catch (IOException e){
//            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("Success",HttpStatus.OK);
//    }
//
//}
