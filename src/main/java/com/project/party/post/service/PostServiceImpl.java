package com.project.party.post.service;

import com.project.file_service.FileUploadService;
import com.project.party.post.dao.PostMapper;
import com.project.party.post.dto.PostDto;
import com.project.party.post.dto.PostImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    private final FileUploadService fileUploadService;

    //파일 업로드 경로
    @Value("${file.location}")
    private String imgPath;

    @Override
    public void insertPost(PostDto postDto) {
        postMapper.insertPost(postDto);
    }

    @Override
    public List<PostDto> getList() {
        return postMapper.getList();
    }

    @Override
    public PostDto getDetail(int postId) {

        //getImageByPostId(postId);

        return postMapper.getDetail(postId);
    }

    @Override
    public void updatePost(PostDto postDto, MultipartFile image) {
        try {
            //게시글 수정
            postMapper.updatePost(postDto);
            PostImageDto imageDto = new PostImageDto();
            MultipartFile newImage = image;
            //새로운 이미지가 들어왔을 경우
            if (!newImage.isEmpty()) {
                int postId = postDto.getPostId();
                //이미지 삭제
                deleteImagesByPostId(postId);
                //이미지 경로
                String imageUrl = fileUploadService.uploadFile(newImage);
                //이미지 경로 저장
                imageDto.setImageUrl(imageUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deletePost(int postId) {
        // 먼저 게시물에 연관된 이미지들을 삭제
        deleteImagesByPostId(postId);
        // 그 후 게시물 삭제
        postMapper.deletePost(postId);
    }

    @Override
    public void insertImage(MultipartFile image, int postId) {
        //이미지 파일 업로드 후 경로 가져오기
        String path = fileUploadService.uploadFile(image);

        PostImageDto postImageDto = new PostImageDto();
        postImageDto.setPostId(postId);
        postImageDto.setImageUrl(path);
        //dto에 저장된 이미지 파일 추출
        postMapper.insertImage(postImageDto);
    }


    //이미지 삭제 메소드
    @Override
    public void deleteImagesByPostId(int postId) {
        postMapper.deleteImagesByPostId(postId);
    }

    //조회수 증가 메소드
    @Override
    public void incrementViewCount(int postId) {
        postMapper.incrementViewCount(postId);
    }

    //나의 글 조회 메소드
    @Override
    public List<PostDto> getMyList(int userNum) {
        //dto객체에 사용자로부터 입력받은 userNum을 저장시킴
        PostDto dto = new PostDto();
        dto.setUserNum(userNum);
        //특정 작성자가 작성한 게시글 목록 조회
        List<PostDto> myList = postMapper.getMyList(userNum);
        //게시글 목록 반환
        return myList;
    }

    //사용자 번호로 게시글 번호 조회
    @Override
    public List<Integer> getPostId(int userNum) {
        PostDto dto = new PostDto();
        dto.setUserNum(userNum);
        List<Integer> postList = postMapper.getPostId(userNum);
        return postList;
    }
}
