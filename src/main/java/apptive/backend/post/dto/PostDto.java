package apptive.backend.post.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostDto {
    private String category;

    private String postTitle;

    private String postContent;

    private MultipartFile postPhoto;

    private Long isLike;
}
