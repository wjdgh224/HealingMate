package apptive.backend.post.service;

import apptive.backend.post.dto.PostDto;
import apptive.backend.post.dto.PostResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostResponseDto savePost(HttpServletRequest request, Long memberId, PostDto postDto) throws Exception;

    PostResponseDto getPost(Long id);

    List<PostResponseDto> getPosts();

    PostResponseDto changePost(HttpServletRequest request, Long id, PostDto postDto) throws Exception;

    void deletePost(Long id) throws Exception;
}
