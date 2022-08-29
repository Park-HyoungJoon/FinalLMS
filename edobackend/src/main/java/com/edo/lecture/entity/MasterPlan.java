package com.edo.lecture.entity;

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
//학습자료
public class MasterPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long MPId;

    private String MPName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LDId")
    private Lec_Divide lec_divide;
}
