package apptive.backend.post.controller;

import apptive.backend.post.dto.PostDto;
import apptive.backend.post.dto.PostResponseDto;
import apptive.backend.post.entity.Post;
import apptive.backend.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/{memberId}") // @RequestBody는 Json
    public ResponseEntity<PostResponseDto> createPost(HttpServletRequest request, @PathVariable Long memberId, PostDto postDto) throws Exception{
        PostResponseDto postResponseDto = postService.savePost(request, memberId, postDto);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);

    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPost(postId);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> postResponseDto = postService.getPosts();

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostResponseDto> changePost(HttpServletRequest request, @PathVariable Long postId, PostDto postDto) throws Exception {
        PostResponseDto postResponseDto = postService.changePost(request, postId, postDto);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) throws Exception {
        postService.deletePost(postId);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
