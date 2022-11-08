package com.edo.community.entity;

import com.edo.community.constant.Section;
import com.edo.user.entity.Member;
import com.edo.util.item.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

//    member의 id를 알아서 외래키로 잡아준다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
//   member의 mappedBy가 member니까 member로 해야 한다.
    private Member member;

//    제목
    @Column
    private String title;

//    내용
    @Column(columnDefinition = "LONGTEXT")
    private String content;

//    조회수
    @Column
    @ColumnDefault("0")
    private int hit;

//    구분
    @Column
    private String category;

    public Community hitCount(int hit){
        this.hit = hit + 1;
        return this;
    }


}
