package apptive.backend.post.service;

import apptive.backend.post.dto.PostDto;
import apptive.backend.post.dto.PostResponseDto;
import apptive.backend.post.entity.Post;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.naming.ldap.PagedResultsControl;
import java.io.IOException;
import java.util.List;

public interface PostService {
    PostResponseDto savePost(Long memberId, PostDto postDto) throws Exception;

    PostResponseDto getPost(Long id);

    Page<Post> getPosts(PageRequest pageRequest);

    Page<Post> getByCategory(String keyword, Pageable pageable);

    Page<Post> getByPostTitle(String keyword, Pageable pageable);

    Page<Post> getByPostContent(String keyword, Pageable pageable);

    Page<Post> getByWriter(String keyword, Pageable pageable);

    PostResponseDto changePost(Long id, PostDto postDto) throws Exception;

    void deletePost(Long id) throws Exception;
}
