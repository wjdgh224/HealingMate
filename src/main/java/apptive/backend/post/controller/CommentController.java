package apptive.backend.post.controller;

import apptive.backend.post.dto.CommentDto;
import apptive.backend.post.dto.CommentResponseDto;
import apptive.backend.post.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(Long postId ,@RequestBody CommentDto commentDto) {
        CommentResponseDto commentResponseDto = commentService.saveComment(postId, commentDto);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @GetMapping("/comment")
    public ResponseEntity<CommentResponseDto> getComment(Long id) {
        CommentResponseDto commentResponseDto = commentService.getComment(id);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(Long postId) {
        List<CommentResponseDto> commentResponseDto = commentService.getComments(postId);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @PutMapping("/comment")
    public ResponseEntity<CommentResponseDto> changeComment(Long id, @RequestBody CommentDto commentDto) throws Exception{
        CommentResponseDto commentResponseDto = commentService.changeComment(id, commentDto);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<String> deleteComment(Long id) throws Exception{
        commentService.deleteComment(id);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

}
