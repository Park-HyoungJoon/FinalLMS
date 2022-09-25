package com.edo.community.dto;

import com.edo.community.entity.Community;
import com.edo.user.entity.Users;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommunityDto {

    Users id;
    String title;
    Users nickname;
    int hits;

    public void communityDto(Users id, String title, Users nickname, int hits){
       this.id = id;
       this.title = title;
       this.nickname = nickname;
       this.hits = hits;
    }

}

