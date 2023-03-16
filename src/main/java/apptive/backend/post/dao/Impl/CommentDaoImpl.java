package apptive.backend.post.dao.Impl;

import apptive.backend.post.dao.CommentDao;
import apptive.backend.post.dto.CommentDto;
import apptive.backend.post.entity.Comment;
import apptive.backend.post.entity.Post;
import apptive.backend.post.repository.CommentRepository;
import apptive.backend.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CommentDaoImpl implements CommentDao {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentDaoImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment insertComment(Long postId, Comment comment) {
        Post selectedPost = postRepository.getById(postId);
        comment.setPost(selectedPost);
        Comment savedComment = commentRepository.save(comment);

        return savedComment;
    }

    @Override
    public Comment selectComment(Long id) {
        Comment selectedComment = commentRepository.getById(id);

        return selectedComment;
    }

    @Override
    public List<Comment> selectComments(Long postId) {
        List<Comment> selectedComments = postRepository.findById(postId).get().getCommentList();

        return selectedComments;
    }

    @Override
    public Comment updateComment(Long id, CommentDto commentDto) throws Exception{
        Optional<Comment> selectedComment = commentRepository.findById(id);

        Comment updatedComment;
        if(selectedComment.isPresent()) {
            Comment comment = selectedComment.get();
            comment.setCommentContent(commentDto.getCommentContent());
            comment.setUpdatedAt(LocalDateTime.now());
            updatedComment = commentRepository.save(comment);
        } else {
            throw new Exception();
        }

        return updatedComment;
    }

    @Override
    public void deleteComment(Long id) throws Exception{
        Optional<Comment> selectedComment = commentRepository.findById(id);

        if(selectedComment.isPresent()) {
            Comment  comment = selectedComment.get();
            commentRepository.delete(comment);
        } else {
            throw new Exception();
        }

    }
}
