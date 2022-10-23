package com.edo.lecture.entity;

import com.edo.lecture.service.LectureContentsService;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
//차시
public class LectureDivide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_divide_id")
    private Long id;

    //강좌 매핑(다대일 / 강좌Id가 테이블에 등록됨)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lecture_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;

    //차시제목
    @Column(length = 255)
    private String lectureDivideTitle;

    //차시순번
    private int lectureDivideSeq;

}
