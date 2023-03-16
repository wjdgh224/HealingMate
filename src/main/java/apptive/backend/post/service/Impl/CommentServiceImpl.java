package apptive.backend.post.service.Impl;

import apptive.backend.post.dao.CommentDao;
import apptive.backend.post.dto.CommentDto;
import apptive.backend.post.dto.CommentResponseDto;
import apptive.backend.post.entity.Comment;
import apptive.backend.post.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public CommentResponseDto saveComment(Long postId, CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setCommentContent(commentDto.getCommentContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment savedComment = commentDao.insertComment(postId, comment);

        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setCommentId(savedComment.getCommentId());
        commentResponseDto.setCommentContent(savedComment.getCommentContent());

        return commentResponseDto;
    }

    @Override
    public CommentResponseDto getComment(Long id) {
        Comment comment = commentDao.selectComment(id);

        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setCommentId(comment.getCommentId());
        commentResponseDto.setCommentContent(comment.getCommentContent());

        return commentResponseDto;
    }

    // postId로 comments조회.
    @Override
    public List<CommentResponseDto> getComments(Long postId) {
        List<Comment> comments = commentDao.selectComments(postId);
        List<CommentResponseDto> responseComments = new ArrayList<>();
        for(Comment comment : comments) {
            CommentResponseDto responseComment = new CommentResponseDto();
            responseComment.setCommentId(comment.getCommentId());
            responseComment.setCommentContent(comment.getCommentContent());
            responseComments.add(responseComment);
        }
        return responseComments;
    }

    @Override
    public CommentResponseDto changeComment(Long id, CommentDto commentDto) throws Exception {
        Comment changedComment = commentDao.updateComment(id, commentDto);

        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setCommentId(changedComment.getCommentId());
        commentResponseDto.setCommentContent(changedComment.getCommentContent());

        return commentResponseDto;
    }

    @Override
    public void deleteComment(Long id) throws Exception {
        commentDao.deleteComment(id);
    }


}
