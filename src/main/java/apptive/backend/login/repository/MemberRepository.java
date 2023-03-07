package apptive.backend.login.repository;

import apptive.backend.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Long> findAllByMemberNickName(String memberNickname);
}
