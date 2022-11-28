package com.edo.lecture.service;

import com.edo.lecture.dto.LectureAddDto;
import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureMember;
import com.edo.lecture.repository.LectureRepository;
import com.edo.lecture.repository.LectureSubscribeRepository;
import com.edo.util.pagination.Paged;
import com.edo.util.pagination.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Configurable
public class LectureService {
    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    LectureSubscribeRepository lectureSubscribeRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository){
        this.lectureRepository= lectureRepository;
    }



    public String getLectureImgeById(Long lectureId){
        String lectureImg = lectureRepository.findLectureImageById(lectureId);
        return lectureImg;
    }
    //LectureId를 이용하여 Lecture객체 얻기
    public Lecture getLectureById(Long lectureId){
        log.info("@!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+lectureId);
        Optional<Lecture> lecture = lectureRepository.findById(lectureId);
        return lecture.get();
    }

    public Lecture lectureAdd(LectureAddDto lectureAddDto){
        Lecture lecture = lectureAddDto.dtoToLecture();
        Lecture lecture1 = lectureRepository.save(lecture);
        return lecture1;
    }
    public Paged<Lecture> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Lecture> postPage = lectureRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    /***
     * PageRequest란
     * 몇 페이지, 한 페이지의 사이즈, Sorting 방법(Option)을 가지고,
     * Repository에 Paging을 요청할 때 사용하는 것
     * PageRequest.of의 경우 2번째 매개변수 까지는 필수 입력이고 3번째 매개변수는 선택이다.
     * @param pageNumber
     * @param size
     * @param part
     * @return AllArgsConstructor로 만들어진 생성자에 파라미터 값 주입.
     */
    public Paged<Lecture> getPageByPart(int pageNumber, int size,String part) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Lecture> postPage;
        if(part.equals("All")){
            //part가 All이면
            postPage = lectureRepository.findAll(request);
        }
        else {
            //part가 파이썬 혹은 ai 등이라면
            postPage = lectureRepository.findAllByLecturePart(request, part);
        }
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    //Lecture_member_id찾기
    public Long findlikeId(Long id, Long lectureId) {
        try{
            Long likeId = lectureSubscribeRepository.searchIdByLectureAndMember(id,lectureId);
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@likeId"+likeId);
            return likeId;

        }catch (Exception e){
            return 0L;
        }
    }

    public LectureMember saveLectureListen(LectureMember lectureMember){
        LectureMember lectureMember1 = lectureSubscribeRepository.save(lectureMember);
        return lectureMember1;
    }

}
