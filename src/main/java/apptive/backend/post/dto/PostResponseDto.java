package apptive.backend.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostResponseDto {
    private Long postId;

    private String category;

    private String postTitle;

    private String postContent;

    private List<String> postPhotos;

    private Long isLike;
}
