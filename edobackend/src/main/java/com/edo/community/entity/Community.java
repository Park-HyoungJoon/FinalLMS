package com.edo.community.entity;

import com.edo.community.constant.Section;
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
    
//    member의 id를 알아서 외래키로 잡아준다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
//   member의 mappedBy가 member니까 member로 해야 한다.
    private Member member;

//    제목
    @Column
    private String communityTitle;

//    내용
    @Column
    private String communityContents;

//    조회수
    @Column
    private int communityHits;

//    구분
    @Enumerated(EnumType.STRING)
    @Column
    private Section communitySection;


}
