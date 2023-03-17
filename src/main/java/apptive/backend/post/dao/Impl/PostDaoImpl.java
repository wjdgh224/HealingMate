package apptive.backend.post.dao.Impl;

import apptive.backend.login.domain.Member;
import apptive.backend.login.repository.MemberRepository;
import apptive.backend.post.dao.PostDao;
import apptive.backend.post.dto.PostDto;
import apptive.backend.post.entity.Post;
import apptive.backend.post.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

// bean for DI
@Component
public class PostDaoImpl implements PostDao {
    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final String path = getClass().getResource("/static/images/").getPath();

    @Autowired
    public PostDaoImpl(MemberRepository memberRepository, PostRepository postRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Post insertPost(Long memberId, Post post) {
        Member selectedMember = memberRepository.getById(memberId);
        post.setMember(selectedMember);
        Post savedProduct = postRepository.save(post);

        return savedProduct;
    }

    @Override
    public Post selectPost(Long id) {
        Post selectedPost = postRepository.getById(id);

        return selectedPost;
    }

    @Override
    public List<Post> selectPosts() {
        List<Post> selectedPost = postRepository.findAll();

        return selectedPost;
    }

    @Override
    public Post updatePost(HttpServletRequest request, Long id, PostDto postDto) throws Exception {
        Optional<Post> selectedPost = postRepository.findById(id);
        String path = updateFile(request, postDto.getPostPhoto());

        Post updatedPost;
        if(selectedPost.isPresent()) {
            Post post = selectedPost.get(); // already exist data = fixed id = update
            post.setPostTitle(postDto.getPostTitle());
            post.setCategory(postDto.getCategory());
            post.setPostContent(postDto.getPostContent());
            post.setPostPhoto(path);
            post.setIsLike(postDto.getIsLike());
            post.setUpdatedAt(LocalDateTime.now());
            updatedPost = postRepository.save(post);
        } else {
            throw new Exception();
        }

        return updatedPost;
    }

    public String updateFile(HttpServletRequest request, MultipartFile file) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        Calendar now = Calendar.getInstance();
        String nowTime = sdf.format(now.getTime());

        String fileType = file.getContentType(); //확장자명 추출 후(마지막 .) path에 적용
        String ex = fileType.substring(fileType.indexOf("/")+1, fileType.length());
        String path = this.path + nowTime + "." + ex;
        file.transferTo(new File(path));

        return nowTime + "." + ex;
    }

    @Override
    public void deletePost(Long id) throws Exception {
        Optional<Post> selectedPost = postRepository.findById(id);

        if(selectedPost.isPresent()) {
            Post post = selectedPost.get();
            postRepository.delete(post);
        } else{
            throw new Exception();
        }
    }
}
