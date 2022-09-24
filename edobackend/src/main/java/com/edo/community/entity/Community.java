package com.edo.community.entity;

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

//    한 명의 유저가 많은 게시글을 올릴 수 있으니까.
    @JoinColumn(name ="users_id" )
    private int id;

//    제목
    @Column(name = "community_Title")
    private String title;

//    내용
    @Column(name = "community_content")
    private String content;

//    조회수
    @Column(name = "community_hits")
    private int hits;

//    삭제 여부 버튼 ㄴ
    @Column(name = "community_deleteYn", length = 1)
    private char deleteYn ='N';
}
