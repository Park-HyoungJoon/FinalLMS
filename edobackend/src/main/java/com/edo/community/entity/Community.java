package com.edo.community.entity;

import com.edo.user.entity.Member;
import com.edo.util.item.BaseTimeEntity;
import lombok.*;


import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "community")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private int communityId;

// 외래키로 users의 id를 받아온다
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id" )
    private Member id;

//    외래키로 users의 nickname을 받아온다.
//    referencedColumnName = PK 가 아닌 필드를 참조할 때 사용하는 옵션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nickname", referencedColumnName = "nickname")
    private Member nickname;

//    제목
    @Column
    private String title;

//    내용
    @Column
    private String content;

//    조회수
    @Column(name = "community_hits")
    private int hits;

////    삭제 여부 버튼
//    @Column(name = "community_deleteYn", length = 1)
//    private char deleteYn ='N';
}
