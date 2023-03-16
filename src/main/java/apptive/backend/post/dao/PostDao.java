package apptive.backend.post.dao;

import apptive.backend.post.dto.PostDto;
import apptive.backend.post.entity.Post;

public interface PostDao {
    Post insertPost(Post post);

    Post selectPost(Long id);

    Post updatePost(Long id, PostDto postDto) throws Exception;

    void deletePost(Long id) throws Exception;

}
