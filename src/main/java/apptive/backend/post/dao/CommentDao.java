package apptive.backend.post.dao;

import apptive.backend.post.dto.CommentDto;
import apptive.backend.post.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentDao {
    Comment insertComment(Long postId, Comment comment);

    Comment selectComment(Long id);

    Page<Comment> selectComments(Long postId, Pageable pageable);

    Comment updateComment(Long id, CommentDto commentDto) throws Exception;

    void deleteComment(Long id) throws Exception;
}
