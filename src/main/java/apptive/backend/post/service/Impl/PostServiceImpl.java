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
        String path = updateFile(request, postDto.getPostPhoto());
        Post post = new Post();
        post.setPostTitle(postDto.getPostTitle());
        post.setCategory(postDto.getCategory());
        post.setPostContent(postDto.getPostContent());
        post.setPostPhoto(path);
        post.setIsLike(postDto.getIsLike());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post savedPost = postDao.insertPost(memberId, post);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostId(savedPost.getPostId());
        postResponseDto.setPostTitle(savedPost.getPostTitle());
        postResponseDto.setCategory(savedPost.getCategory());
        postResponseDto.setPostContent(savedPost.getPostContent());
        postResponseDto.setPostPhoto(savedPost.getPostPhoto());
        postResponseDto.setIsLike(savedPost.getIsLike());

        return postResponseDto;
    }

    public String updateFile(HttpServletRequest request, MultipartFile file) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        Calendar now = Calendar.getInstance();
        String nowTime = sdf.format(now.getTime());
        //System.out.println(nowTime);

        String fileType = file.getContentType(); //확장자명 추출 후(마지막 .) path에 적용
        String ex = fileType.substring(fileType.indexOf("/")+1, fileType.length());
        //System.out.println(ex);
        String path = this.path + nowTime + "." + ex;
        // 이미지 저장 디렉토리 *** 경로는 고정 되어 있으니 전역변수로 쓰고 파일 이름만 저장 + 생성 날자
        file.transferTo(new File(path));

        return nowTime + "." + ex;
    }

    @Override
    public PostResponseDto getPost(Long id) {
        Post post = postDao.selectPost(id);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostId(post.getPostId());
        postResponseDto.setPostTitle(post.getPostTitle());
        postResponseDto.setCategory(post.getCategory());
        postResponseDto.setPostContent(post.getPostContent());
        postResponseDto.setPostPhoto(this.path + post.getPostPhoto());
        postResponseDto.setIsLike(post.getIsLike());

        return postResponseDto;
    }

    @Override
    public List<PostResponseDto> getPosts() {
        List<Post> posts = postDao.selectPosts();
        List<PostResponseDto> responseDtoes = new ArrayList<>();
        for(Post post : posts) {
            PostResponseDto responsePost = new PostResponseDto();
            responsePost.setPostId(post.getPostId());
            responsePost.setPostTitle(post.getPostTitle());
            responsePost.setCategory(post.getCategory());
            responsePost.setPostContent(post.getPostContent());
            responsePost.setPostPhoto(post.getPostPhoto());
            responsePost.setIsLike(post.getIsLike());
            responseDtoes.add(responsePost);
        }
        return responseDtoes;
    }

    @Override
    public PostResponseDto changePost(HttpServletRequest request, Long id, PostDto postDto) throws Exception {
        Post changedPost = postDao.updatePost(request, id, postDto);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostId(changedPost.getPostId());
        postResponseDto.setPostTitle(changedPost.getPostTitle());
        postResponseDto.setCategory(changedPost.getCategory());
        postResponseDto.setPostContent(changedPost.getPostContent());
        postResponseDto.setPostPhoto(changedPost.getPostPhoto());
        postResponseDto.setIsLike(changedPost.getIsLike());

        return postResponseDto;
    }

    @Override
    public void deletePost(Long id) throws Exception{
        postDao.deletePost(id);
    }
}
