package com.edo.user.entity;

import com.edo.lecture.entity.Lecture;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
//강사-강좌매핑
public class Teacher_Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long TLId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LId")
    private Lecture lecture;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TId")
    private Teacher teacher;
}
