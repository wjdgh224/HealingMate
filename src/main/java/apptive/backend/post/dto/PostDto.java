package apptive.backend.post.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostDto {
    private String category;

    private String postTitle;

    private String postContent;

    private List<MultipartFile> postPhotos;

    private Long isLike;
}
