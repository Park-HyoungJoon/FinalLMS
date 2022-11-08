package com.edo.lecture.entity;

import com.edo.lecture.dto.LectureAddDto;
import com.edo.lecture.dto.LectureDivideAndContentsDto;
import com.edo.lecture.repository.LectureDivideRepository;
import com.edo.lecture.repository.LectureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //이 친구가 test면 만들었다가 지워버림
@Rollback(value = true) // Rollback이  false이면 Transactional 무시하고 db에 생성
class LectureTest {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    LectureDivideRepository lectureDivideRepository;


    public Lecture createLecture(){
        LectureAddDto lectureAddDto = new LectureAddDto();
        lectureAddDto.setLectureDetail("파이썬 기초학습");
        lectureAddDto.setLectureImage("b685bd37-20e6-4a33-9cc3-3f16cef163fb_images.jfif");
        lectureAddDto.setLectureInfo("<p><strong>입문자</strong>를 위해 준비한<br><strong>[프로그래밍 언어] 강의입니다.</strong></p><p>이미 만명 이상이 학습하고 만족한 최고의 프로그래밍 입문 강의. 인프런이 비전공자 위치에서 직접 기획하고 준비한 프로그래밍 입문 강의로, 프로그래밍을 전혀 접해보지 못한 사람부터 실제 활용 가능한 프로그래밍 능력까지 갈 수 있도록 도와주는 강의입니다.</p><p>✍️<br>이런 걸<br>배워요!</p><p>입문자도 쉽게 할 수 있는 프로그래밍 입문</p><p>파이썬 기초 문법과 활용법을 배울 수 있어요</p><p>데이터 분석</p><p>업무 자동화</p><h3>숙제, 과제, 업무...<br>해야 하지만 늘 우리를 고통스럽게 하는 것들,<br>어떻게 하면 하루가 좀 더 편해질 수 있을까요?</h3><p><img src=\"https://cdn.inflearn.com/public/files/courses/324145/58580492-9527-4e3f-9a3d-593c4bdbfee7/0.gif\"></p><p><img src=\"https://cdn.inflearn.com/public/files/courses/324145/cda9b423-a410-497a-ae22-8d36b100bf99/1.png\"></p><p><img src=\"https://cdn.inflearn.com/public/files/courses/324145/129d8e36-3974-4ebe-a5fa-e4bd82f8ac0f/speak.gif\"></p><h3>프로그래밍이 우리에게 자유를 줄 수단이라서가 아닐까요?&nbsp;</h3><p><i>예시)</i></p><ul><li>과제를 위한 자료 찾기를 클릭 한 번으로!</li><li>수많은 거래처에 보낼 문서를 엔터 한 번으로!</li><li>매달 해야 하는 반복 업무를 컴퓨터가 자동으로!</li></ul><p>최근 코딩과 관련된 교육 과목이 증가하면서 프로그래밍과 관련된 교육/직무에 관한 관심도 많이 증가하고 있죠. 게다가 수많은 기업들이나 팀에서 코딩을 필수 덕목으로 생각하기 시작했어요. 얼마 전 대기업 입사 면접에서 비전공자들에게 파이썬 할 줄 아냐고 물어보기도 했죠.</p><p>프로그래밍을 취미로 투잡하는 사람들, 여행 다니며 일하는 만들고 싶은 것을 만드는 노마드 인생을 즐기는 사람들도 늘어나고 있어요. (헉, 이런 <strong>생산적인 취미</strong>가...!)</p><p><img src=\"https://cdn.inflearn.com/public/files/courses/324145/6ff3b56a-9ac8-4b31-8f38-8b3f71db6677/2.png\"></p><h3>고민은 이제 그만!&nbsp;<br>누구나 재미있게 차근차근<br>프로그래밍을 배울 수 있어요.&nbsp;</h3><p><a href=\"http://inf-mindmap.s3-website.ap-northeast-2.amazonaws.com/inflearn-python-courses-807b09e61479572aac84b4130be7a6a2.html\"><img src=\"https://cdn.inflearn.com/public/files/courses/324145/9b09b0a8-ea66-4743-8aba-fd5c1395fc84/optimize.jfif\"></a></p>");
        lectureAddDto.setPart("python");
        lectureAddDto.setLectureTime("매주 월요일 3시");
        lectureAddDto.setLectureTitle("파이썬 기초");

        Lecture lecture = lectureAddDto.dtoToLecture();
        return lecture;
    }


    @Test
    @DisplayName("파이썬 강좌추가")
    public void pythonLectureAdd(){
        Lecture lecture = createLecture();
        for(int i=0; i<100; i++){
           Lecture lecture1 =  lectureRepository.save(lecture);
            LectureDivideAndContentsDto lectureDivideAndContentsDto = new LectureDivideAndContentsDto();
            lectureDivideAndContentsDto.setLecture(lecture1);

        }
    }
}