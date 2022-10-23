package com.edo.lecture.entity;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LectureDivide lectureDivide;


    //강의자료 제목
    @Column(length = 255)
    private String lectureContentsTitle;

    private String lectureContentsInfo;
}
