package apptive.backend.post.dao;

import apptive.backend.post.dto.PostDto;
import apptive.backend.post.entity.Post;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostDao {
    Post insertPost(Long memberId, Post post);

    Post selectPost(Long id);

    Page<Post> selectPosts(PageRequest pageRequest);

    Page<Post> selectByCategory(String keyword, Pageable pageable);

    Page<Post> selectByPostTitle(String keyword, Pageable pageable);

    Page<Post> selectByPostContent(String keyword, Pageable pageable);

    Page<Post> selectByWriter(String keyword, Pageable pageable);

    Post updatePost(Long id, PostDto postDto) throws Exception;

    void deletePost(Long id) throws Exception;

}
