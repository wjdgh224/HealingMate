package apptive.backend.post.dto;

import lombok.Data;

@Data
public class PostResponseDto {
    private Long postId;

    private String category;

    private String postTitle;

    private String postContent;

    private String postPhoto;

    private Long isLike;
}
