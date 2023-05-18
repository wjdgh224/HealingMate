package apptive.backend.post.service.Impl;

import apptive.backend.config.AwsS3;
import apptive.backend.post.dao.PostDao;
import apptive.backend.post.dto.PostDto;
import apptive.backend.post.dto.PostResponseDto;
import apptive.backend.post.entity.Post;
import apptive.backend.post.service.PostService;
import apptive.backend.post.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostDao postDao;
    private final S3Service s3Service;
    @Autowired
    public PostServiceImpl(PostDao postDao, S3Service s3Service) {
        this.postDao = postDao;
        this.s3Service = s3Service;
    }

    @Override
    public PostResponseDto savePost(Long memberId, PostDto postDto) throws Exception{
        List<AwsS3> awsS3s = s3Service.upload(postDto.getPostPhotos(), "upload");
        List<String> path = new ArrayList<>();
        for(int i=0; i<awsS3s.size(); i++) {
            System.out.println(awsS3s.get(i).getKey() + " " + awsS3s.get(i).getPath());
            path.add(awsS3s.get(i).getPath());
        }
        Post post = new Post();
        post.setPostTitle(postDto.getPostTitle());
        post.setCategory(postDto.getCategory());
        post.setPostContent(postDto.getPostContent());
        post.setPostPhotos(path);
        post.setIsLike(postDto.getIsLike());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post savedPost = postDao.insertPost(memberId, post);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostId(savedPost.getPostId());
        postResponseDto.setPostTitle(savedPost.getPostTitle());
        postResponseDto.setCategory(savedPost.getCategory());
        postResponseDto.setPostContent(savedPost.getPostContent());
        postResponseDto.setPostPhotos(savedPost.getPostPhotos());
        postResponseDto.setIsLike(savedPost.getIsLike());

        return postResponseDto;
    }

    @Override
    public PostResponseDto getPost(Long id) {
        Post post = postDao.selectPost(id);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostId(post.getPostId());
        postResponseDto.setPostTitle(post.getPostTitle());
        postResponseDto.setCategory(post.getCategory());
        postResponseDto.setPostContent(post.getPostContent());
        List<String> list = new ArrayList<>();
        for(String photo : post.getPostPhotos()) {
            list.add(photo);
        }
        postResponseDto.setPostPhotos(list);
        postResponseDto.setIsLike(post.getIsLike());

        System.out.println(postResponseDto);
        return postResponseDto;
    }

    @Override
    public Page<Post> getPosts(PageRequest pageRequest) {
        Page<Post> posts = postDao.selectPosts(pageRequest);
        return posts;
    }

    @Override
    public Page<Post> getByCategory(String keyword, Pageable pageable) {
        Page<Post> posts = postDao.selectByCategory(keyword, pageable);

        return posts;
    }

    @Override
    public Page<Post> getByPostTitle(String keyword, Pageable pageable) {
        Page<Post> posts = postDao.selectByPostTitle(keyword, pageable);

        return posts;
    }

    @Override
    public Page<Post> getByPostContent(String keyword, Pageable pageable) {
        Page<Post> posts = postDao.selectByPostContent(keyword, pageable);

        return posts;
    }

    @Override
    public Page<Post> getByWriter(String keyword, Pageable pageable) {
        Page<Post> posts = postDao.selectByWriter(keyword, pageable);

        return posts;
    }

    @Override
    public PostResponseDto changePost(Long id, PostDto postDto) throws Exception {
        Post changedPost = postDao.updatePost(id, postDto);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostId(changedPost.getPostId());
        postResponseDto.setPostTitle(changedPost.getPostTitle());
        postResponseDto.setCategory(changedPost.getCategory());
        postResponseDto.setPostContent(changedPost.getPostContent());
        postResponseDto.setPostPhotos(changedPost.getPostPhotos());
        postResponseDto.setIsLike(changedPost.getIsLike());

        return postResponseDto;
    }

    @Override
    public void deletePost(Long id) throws Exception{
        postDao.deletePost(id);
    }
}
