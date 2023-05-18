package apptive.backend.post.dao.Impl;

import apptive.backend.config.AwsS3;
import apptive.backend.login.domain.Member;
import apptive.backend.login.repository.MemberRepository;
import apptive.backend.post.dao.PostDao;
import apptive.backend.post.dto.PostDto;
import apptive.backend.post.entity.Post;
import apptive.backend.post.repository.PostRepository;
import apptive.backend.post.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// bean for DI
@Component
public class PostDaoImpl implements PostDao {
    private final MemberRepository memberRepository;

    private final PostRepository postRepository;
    private final S3Service s3Service;

    @Autowired
    public PostDaoImpl(MemberRepository memberRepository, PostRepository postRepository, S3Service s3Service) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.s3Service = s3Service;
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
    public Page<Post> selectPosts(PageRequest pageRequest) {
        Page<Post> selectedPost = postRepository.findAll(pageRequest);

        return selectedPost;
    }

    @Override
    public Page<Post> selectByCategory(String keyword, Pageable pageable) {
        Page<Post> selectByCategory = postRepository.findByCategory(keyword, pageable);

        return selectByCategory;
    }

    @Override
    public Page<Post> selectByPostTitle(String keyword, Pageable pageable) {
        Page<Post> selectByPostTitle = postRepository.findByPostTitleContaining(keyword, pageable);

        return selectByPostTitle;
    }

    @Override
    public Page<Post> selectByPostContent(String keyword, Pageable pageable) {
        Page<Post> selectByPostContent = postRepository.findByPostContentContaining(keyword, pageable);

        return selectByPostContent;
    }

    @Override
    public Page<Post> selectByWriter(String keyword, Pageable pageable) {
        Optional<Member> member = memberRepository.findByMemberNickname(keyword);
        Page<Post> selectByWriter = postRepository.findByMemberMemberId(member.get().getMemberId(), pageable);

        return selectByWriter;
    }

    @Override
    public Post updatePost(Long id, PostDto postDto) throws Exception {
        Optional<Post> selectedPost = postRepository.findById(id); // 기존의 데이터

        List<AwsS3> awsS3s = s3Service.upload(postDto.getPostPhotos(), "upload");
        List<String> path = new ArrayList<>();
        for(int i=0; i<awsS3s.size(); i++) {
            path.add(awsS3s.get(i).getPath());
        }

        Post updatedPost;
        if(selectedPost.isPresent()) {
            Post post = selectedPost.get(); // already exist data = fixed id = update
            List<String> photos = post.getPostPhotos(); // 기존에 들어 있는 이미지 정보로 S3 삭제
            for(int i=0; i<photos.size(); i++) {
                String p = photos.get(i);
                String []key = p.split("/");
                AwsS3 awsS3 = new AwsS3();
                awsS3.setKey("upload/"+key[key.length-1]);
                awsS3.setPath(p);
                s3Service.remove(awsS3);
            }
            post.setPostTitle(postDto.getPostTitle());
            post.setCategory(postDto.getCategory());
            post.setPostContent(postDto.getPostContent());
            post.setPostPhotos(path);
            post.setIsLike(postDto.getIsLike());
            post.setUpdatedAt(LocalDateTime.now());
            updatedPost = postRepository.save(post);
        } else {
            throw new Exception();
        }

        return updatedPost;
    }

    @Override
    public void deletePost(Long id) throws Exception {
        Optional<Post> selectedPost = postRepository.findById(id);

        if(selectedPost.isPresent()) {
            Post post = selectedPost.get();
            List<String> photos = post.getPostPhotos();
            // S3사진 삭제 작업.
            for(int i=0; i<photos.size(); i++) {
                String path = photos.get(i);
                String []key = path.split("/");
                AwsS3 awsS3 = new AwsS3();
                awsS3.setKey("upload/"+key[key.length-1]);
                awsS3.setPath(path);
                s3Service.remove(awsS3);
            }
            postRepository.delete(post);
        } else{
            throw new Exception();
        }
    }
}
