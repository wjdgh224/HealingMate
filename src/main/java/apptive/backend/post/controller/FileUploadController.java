package apptive.backend.post.controller;

import apptive.backend.config.AwsS3;
import apptive.backend.post.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class FileUploadController {
    private final S3Service awsS3Service;

    @PostMapping("/resource")
    public List<AwsS3> upload(@RequestPart("file") List<MultipartFile> multipartFile) throws IOException {
        return awsS3Service.upload(multipartFile,"upload");
    }

    @DeleteMapping("/resource")
    public void remove(AwsS3 awsS3) {
        awsS3Service.remove(awsS3);
    }

    @GetMapping("/resource")
    public String get(@RequestPart("filename") String filename) {
        return awsS3Service.getUrl(filename);
    }
}
