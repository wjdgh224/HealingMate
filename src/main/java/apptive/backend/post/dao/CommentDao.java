package apptive.backend.post.dao;

import apptive.backend.post.dto.CommentDto;
import apptive.backend.post.entity.Comment;

import java.util.List;

public interface CommentDao {
    Comment insertComment(Long postId, Comment comment);

    Comment selectComment(Long id);

    List<Comment> selectComments(Long postId);

    Comment updateComment(Long id, CommentDto commentDto) throws Exception;

    void deleteComment(Long id) throws Exception;
}
