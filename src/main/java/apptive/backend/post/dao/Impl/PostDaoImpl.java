package apptive.backend.post.dao.Impl;

import apptive.backend.post.dao.PostDao;
import apptive.backend.post.dto.PostDto;
import apptive.backend.post.entity.Post;
import apptive.backend.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

// bean for DI
@Component
public class PostDaoImpl implements PostDao {
    private final PostRepository postRepository;

    @Autowired
    public PostDaoImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post insertPost(Post post) {
        Post savedProduct = postRepository.save(post);

        return savedProduct;
    }

    @Override
    public Post selectPost(Long id) {
        Post selectedPost = postRepository.getById(id);

        return selectedPost;
    }

    @Override
    public Post updatePost(Long id, PostDto postDto) throws Exception {
        Optional<Post> selectedPost = postRepository.findById(id);

        Post updatedPost;
        if(selectedPost.isPresent()) {
            Post post = selectedPost.get(); // already exist data = fixed id = update
            post.setPostTitle(postDto.getPostTitle());
            post.setPostContent(postDto.getPostContent());
            post.setPostPhoto(postDto.getPostPhoto());
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
            postRepository.delete(post);
        } else{
            throw new Exception();
        }
    }
}
