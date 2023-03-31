package apptive.backend.post.repository;

import apptive.backend.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCategory(String keyword, Pageable pageable);
    Page<Post> findByPostTitleContaining(String keyword, Pageable pageable);
    Page<Post> findByPostContentContaining(String keyword, Pageable pageable);
    //Page<Post> findByMemberMemberId(Long id, Pageable pageable);

    //Page<Post> findByMemberNickName(String keyword, Pageable pageable); // 작성자로 id 찾은 후
}
