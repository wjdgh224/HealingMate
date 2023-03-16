package apptive.backend.post.service.Impl;

import apptive.backend.post.dao.PostDao;
import apptive.backend.post.dto.PostDto;
import apptive.backend.post.dto.PostResponseDto;
import apptive.backend.post.entity.Post;
import apptive.backend.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {
    private final PostDao postDao;

    @Autowired
    public PostServiceImpl(PostDao postDao) {
        this.postDao = postDao;
    }

    @Override
    public PostResponseDto savePost(PostDto postDto) {
        Post post = new Post();
        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());
        post.setPostPhoto(postDto.getPostPhoto());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post savedPost = postDao.insertPost(post);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostId(savedPost.getPostId());
        postResponseDto.setPostTitle(savedPost.getPostTitle());
        postResponseDto.setPostContent(savedPost.getPostContent());
        postResponseDto.setPostPhoto(savedPost.getPostPhoto());

        return postResponseDto;
    }

    @Override
    public PostResponseDto getPost(Long id) {
        Post post = postDao.selectPost(id);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostId(post.getPostId());
        postResponseDto.setPostTitle(post.getPostTitle());
        postResponseDto.setPostContent(post.getPostContent());
        postResponseDto.setPostPhoto(post.getPostPhoto());

        return postResponseDto;
    }

    @Override
    public PostResponseDto changePost(Long id, PostDto postDto) throws Exception {
        Post changedPost = postDao.updatePost(id, postDto);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostId(changedPost.getPostId());
        postResponseDto.setPostTitle(changedPost.getPostTitle());
        postResponseDto.setPostContent(changedPost.getPostContent());
        postResponseDto.setPostPhoto(changedPost.getPostPhoto());

        return postResponseDto;
    }

    @Override
    public void deletePost(Long id) throws Exception{
        postDao.deletePost(id);
    }
}
