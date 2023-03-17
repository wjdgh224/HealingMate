package apptive.backend.post.dao;

import apptive.backend.post.dto.PostDto;
import apptive.backend.post.entity.Post;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PostDao {
    Post insertPost(Long memberId, Post post);

    Post selectPost(Long id);

    List<Post> selectPosts();

    Post updatePost(HttpServletRequest request, Long id, PostDto postDto) throws Exception;

    void deletePost(Long id) throws Exception;

}
