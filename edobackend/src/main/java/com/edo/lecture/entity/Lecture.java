package com.edo.lecture.entity;

import com.edo.user.entity.Teacher;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
//강좌
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;
    //제목
    @Column(length = 100)
    private String lectureTitle;
    //개요
    @Column(length = 20)
    private String lectureInfo;
    //신청시작일
    @Column(length = 8)
    private LocalDate startDate;
    //신청마감일
    @Column(length = 8)
    private LocalDate finalDate;
    //상시 신청 여부
    @Column(length = 1)
    private String subYN;
    //운영 시작일
    @Column(length = 8)
    private LocalDate manageStartDate;
    //운영 종료일
    @Column(length = 8)
    private LocalDate manageFinalDate;
    //상시 운영 여부
    @Column(length = 1)
    private String manageYN;
    //강의시간
    @Column(length = 20)
    private String lectureTime;
    //강좌소개
    @Column(columnDefinition = "LONGTEXT")
    private String lectureDetail;
    //대표이미지
    @Column(columnDefinition = "LONGTEXT")
    private String lectureImage;
    //상태
    @Column(length = 1)
    private String lectureYN;
}
