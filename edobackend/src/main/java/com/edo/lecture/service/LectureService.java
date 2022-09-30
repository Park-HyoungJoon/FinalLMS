package com.edo.lecture.service;

import com.edo.lecture.dto.LectureAddDto;
import com.edo.lecture.entity.Lecture;
import com.edo.lecture.repository.LectureRepository;
import com.edo.util.pagination.Paged;
import com.edo.util.pagination.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class LectureService {
    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository){
        this.lectureRepository= lectureRepository;
    }



    //LectureTitle을 이용하여 Lecture객체 얻기
    public Lecture SearchLectureToTitle(String LectureTitle){
        Lecture lecture = lectureRepository.findByLectureTitle(LectureTitle);
        return lecture;
    }

    public String lectureAdd(LectureAddDto lectureAddDto){
        Lecture lecture = lectureAddDto.LectureAddDtoToLecture(lectureAddDto);
        lectureRepository.save(lecture);
        return "잘 보내진 듯 ";
    }
    public Paged<Lecture> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Lecture> postPage = lectureRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }
    public Paged<Lecture> getPageByPart(int pageNumber, int size,String part) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Lecture> postPage = lectureRepository.findAllByLecturePart(request,part);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }
}
