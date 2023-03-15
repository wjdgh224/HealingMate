package apptive.backend.post.controller;

import apptive.backend.post.dto.PostDto;
import apptive.backend.post.dto.PostResponseDto;
import apptive.backend.post.entity.Post;
import apptive.backend.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostDto postDto) {
        PostResponseDto postResponseDto = postService.savePost(postDto);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);

    }

    @GetMapping("/post")
    public ResponseEntity<PostResponseDto> getPost(Long id) {
        PostResponseDto postResponseDto = postService.getPost(id);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @PutMapping("/post")
    public ResponseEntity<PostResponseDto> changePost(Long id, @RequestBody PostDto postDto) throws Exception {
        PostResponseDto postResponseDto = postService.changePost(id, postDto);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @DeleteMapping("/post")
    public ResponseEntity<String> deletePost(Long id) throws Exception {
        postService.deletePost(id);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
