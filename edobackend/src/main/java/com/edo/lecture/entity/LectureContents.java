package com.edo.lecture.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
//강의자료
public class LectureContents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_contents_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_divide_id")
    private LectureDivide lectureDivide;

    //강의자료 제목
    @Column(length = 255)
    private String lectureContentsTitle;

    private String lectureContentsInfo;
}
