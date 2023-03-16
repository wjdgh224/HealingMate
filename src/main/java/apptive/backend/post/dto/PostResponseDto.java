package apptive.backend.post.dto;

import lombok.Data;

@Data
public class PostResponseDto {
    private Long postId;

    private String postTitle;

    private String postContent;

    private String postPhoto;
}
