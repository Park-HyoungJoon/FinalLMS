package com.edo.lecture.entity;

import com.edo.user.entity.Teacher;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
//강좌
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long LId;
    //이름
    private String LName;
    //제목
    private String title;
    //개요
    private String LInfo;
    //신청시작일
    @Temporal(TemporalType.DATE)
    private Date SDate;
    //신청마감일
    @Temporal(TemporalType.DATE)
    private Date FDate;
    //상시 신청 여부
    private String subYN;
    //운영 시작일
    @Temporal(TemporalType.DATE)
    private Date MSDate;
    //운영 종료일
    @Temporal(TemporalType.DATE)
    private Date MFDate;
    //상시 운영 여부
    private String ManageYN;
    //강의시간
    private String LTime;
    //강좌소개
    private String LDetail;
    //대표이미지
    private String LImage;
    //상태
    private String LYN;
    //등록일
    private String LDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
}
