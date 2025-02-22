package com.project.recipe.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {
    private int rplNum;  //댓글 번호
    private int rcpNum; //게시글 번호
    private int userNum; //작성자 번호
    private String rplContent;  //댓글 내용
    private String userNickname; //댓글 작성자
    private String rplDeleted;  //댓글 삭제 여부 ('yes' or 'no')
    private String rplRegdate;  //댓글 작성일
    private String userProfile;  //댓글 작성자 프로필
    private String mainPath;  //메인 이미지 파일 경로

}
