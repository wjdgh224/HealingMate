package apptive.backend.post.controller;

import apptive.backend.post.dto.CommentDto;
import apptive.backend.post.dto.CommentResponseDto;
import apptive.backend.post.entity.Comment;
import apptive.backend.post.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment/{postId}") // 게시글 별 댓글 저장
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId , CommentDto commentDto) {
        CommentResponseDto commentResponseDto = commentService.saveComment(postId, commentDto);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @GetMapping("/comment/{commentId}") // 댓글 단일 조회
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long commentId) {
        CommentResponseDto commentResponseDto = commentService.getComment(commentId);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @GetMapping("/comments/{postId}") // 게시글 별 댓글 조회
    public ResponseEntity<Page<Comment>> getComments(@PathVariable Long postId, @RequestParam(defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Comment> comments = commentService.getComments(postId, pageRequest);
        //List<CommentResponseDto> commentResponseDto = commentService.getComments(postId);

        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @PutMapping("/comment/{commentId}") // 댓글 변경
    public ResponseEntity<CommentResponseDto> changeComment(@PathVariable Long commentId, CommentDto commentDto) throws Exception{
        CommentResponseDto commentResponseDto = commentService.changeComment(commentId, commentDto);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @DeleteMapping("/comment/{commentId}") // 댓글 삭제
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) throws Exception{
        commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

}
