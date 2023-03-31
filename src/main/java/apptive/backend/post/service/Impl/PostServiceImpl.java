package apptive.backend.post.service.Impl;

import apptive.backend.post.dao.PostDao;
import apptive.backend.post.dto.CommentResponseDto;
import apptive.backend.post.dto.PostDto;
import apptive.backend.post.dto.PostResponseDto;
import apptive.backend.post.entity.Comment;
import apptive.backend.post.entity.Post;
import apptive.backend.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostDao postDao;
    private final String path = getClass().getResource("/static/images/").getPath();

    @Autowired
    public PostServiceImpl(PostDao postDao) {
        this.postDao = postDao;
    }

    @Override
    public PostResponseDto savePost(HttpServletRequest request, Long memberId, PostDto postDto) throws Exception{
        List<String> path = updateFiles(request, postDto.getPostPhotos());
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

    public List<String> updateFiles(HttpServletRequest request, List<MultipartFile> files) throws Exception {
        if(files==null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        Calendar now = Calendar.getInstance();
        String nowTime = sdf.format(now.getTime());
        //System.out.println(nowTime);

        List<String> list = new ArrayList<>();
        int i = 1;
        for(MultipartFile file : files) { // 서순 주기
            String fileType = file.getContentType(); //확장자명 추출 후(마지막 .) path에 적용
            String ex = fileType.substring(fileType.indexOf("/")+1, fileType.length());
            String name = nowTime + "_" + i + "." + ex;
            String path = this.path + name;
            file.transferTo(new File(path));
            list.add(name);
            i++;
        }

        return list;
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
            list.add(this.path + photo);
        }
        postResponseDto.setPostPhotos(list);
        postResponseDto.setIsLike(post.getIsLike());

        return postResponseDto;
    }

    @Override
    public Page<Post> getPosts(PageRequest pageRequest) {
        Page<Post> posts = postDao.selectPosts(pageRequest);
//        List<PostResponseDto> responseDtoes = new ArrayList<>();
//        for(Post post : posts) {
//            PostResponseDto responsePost = new PostResponseDto();
//            responsePost.setPostId(post.getPostId());
//            responsePost.setPostTitle(post.getPostTitle());
//            responsePost.setCategory(post.getCategory());
//            responsePost.setPostContent(post.getPostContent());
//            responsePost.setPostPhoto(post.getPostPhoto());
//            responsePost.setIsLike(post.getIsLike());
//            responseDtoes.add(responsePost);
//        }
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
    public PostResponseDto changePost(HttpServletRequest request, Long id, PostDto postDto) throws Exception {
        Post changedPost = postDao.updatePost(request, id, postDto);

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
