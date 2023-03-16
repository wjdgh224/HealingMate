package apptive.backend.post.service;

import apptive.backend.post.dto.PostDto;
import apptive.backend.post.dto.PostResponseDto;

public interface PostService {
    PostResponseDto savePost(PostDto postDto);

    PostResponseDto getPost(Long id);

    PostResponseDto changePost(Long id, PostDto postDto) throws Exception;

    void deletePost(Long id) throws Exception;
}
