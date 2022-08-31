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
//학습자료
public class MasterPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "master_plan_id")
    private Long id;

    @Column(length = 255)
    private String masterPlanName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_divide_id")
    private LectureDivide lectureDivide;
}
