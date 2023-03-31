package apptive.backend.post.service;

import apptive.backend.post.dto.CommentDto;
import apptive.backend.post.dto.CommentResponseDto;
import apptive.backend.post.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    //comment 생성, postId를 주어 comment entity필드에 현재 post를 넘겨주면 post entity에 List<comment>로 넘어감.
    CommentResponseDto saveComment(Long postId, CommentDto commentDto);

    //comment 1개 조회
    CommentResponseDto getComment(Long id);

    //post 모든 댓글 조회
    Page<Comment> getComments(Long postId, Pageable pageable);

    //comment 수정
    CommentResponseDto changeComment(Long id, CommentDto commentDto) throws Exception;

    //comment 삭제
    void deleteComment(Long id) throws Exception;
}
