package apptive.backend.post.controller;

import apptive.backend.post.dto.PostDto;
import apptive.backend.post.dto.PostResponseDto;
import apptive.backend.post.entity.Post;
import apptive.backend.post.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("test")
    public String test() {
        return "test";
    }
    @PostMapping("/post/{memberId}") // @RequestBody는 Json, 맴버별 게시판 저장
    public ResponseEntity<PostResponseDto> createPost(@PathVariable Long memberId, PostDto postDto) throws Exception{
        PostResponseDto postResponseDto = postService.savePost(memberId, postDto);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);

    }

    @GetMapping("/post/{postId}") // 게시글 단일 조회
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPost(postId);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @GetMapping("/posts") // 모든 게시글 조회
    public ResponseEntity<Page<Post>> getPosts(@RequestParam(defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Post> pages = postService.getPosts(pageRequest);
        return  ResponseEntity.status(HttpStatus.OK).body(pages);

    }

    @GetMapping("/posts/category") // 카테고리로 게시글 조회
    public ResponseEntity<Page<Post>> getByCategory(@RequestParam String keyword, @RequestParam(defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Post> pages = postService.getByCategory(keyword, pageRequest);

        return ResponseEntity.status(HttpStatus.OK).body(pages);
    }

    @GetMapping("/posts/title") // 제목으로 게시글 조회
    public ResponseEntity<Page<Post>> getByPostTitle(@RequestParam String keyword, @RequestParam(defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Post> pages = postService.getByPostTitle(keyword, pageRequest);

        return ResponseEntity.status(HttpStatus.OK).body(pages);
    }

    @GetMapping("/posts/content") //내용으로 게시글 조회
    public ResponseEntity<Page<Post>> getByPostContent(@RequestParam String keyword, @RequestParam(defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Post> pages = postService.getByPostContent(keyword, pageRequest);

        return ResponseEntity.status(HttpStatus.OK).body(pages);
    }

    @GetMapping("/posts/writer") // 작성자로 게시글 조회
    public ResponseEntity<Page<Post>> getByWriter(@RequestParam String keyword, @RequestParam(defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Post> pages = postService.getByWriter(keyword, pageRequest);

        return ResponseEntity.status(HttpStatus.OK).body(pages);
    }

    @PutMapping("/post/{postId}") // 게시글 변경
    public ResponseEntity<PostResponseDto> changePost(@PathVariable Long postId, PostDto postDto) throws Exception {
        PostResponseDto postResponseDto = postService.changePost(postId, postDto);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @DeleteMapping("/post/{postId}") // 게시글 삭제
    public ResponseEntity<String> deletePost(@PathVariable Long postId) throws Exception {
        postService.deletePost(postId);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }


}
