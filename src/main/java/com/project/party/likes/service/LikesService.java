package com.project.party.likes.service;

import com.project.party.likes.dto.LikesDto;
import com.project.party.post.dto.PostDto;

import java.util.List;

public interface LikesService {
    //좋아요 토글
    String toggleLike(LikesDto likesDto);
    //좋아요 수
    int countLikes(int postId);
    //좋아요 여부
    boolean isLikedByUser(LikesDto likesDto);
    //좋아요 순위
    List<LikesDto> orderByLike();

}
