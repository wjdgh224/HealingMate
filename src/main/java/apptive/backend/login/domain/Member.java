package apptive.backend.login.domain;

import apptive.backend.config.StringListConverter;
import apptive.backend.login.dto.request.MemberRequestDto;
import apptive.backend.post.entity.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Member implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    @Getter //가입 후 멤버아이디를 전달하기 위해 게터 사용
    private Long memberId;

    @Column(nullable = false)
    private String memberAge;
    @Column(nullable = false)
    private String memberSex;

    // 멤버 닉네임과 비밀번호를 통해 로그인
    @Column(nullable = false)
    private String memberNickname;
    @Column(nullable = false) @Getter
    private String pwd;

    // 질병 추가 리스트 + nullable = true
    @Convert(converter = StringListConverter.class)
    @Column(nullable = false)
    private List<String> diseaseList;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Post> postList = new ArrayList<>();

    //<------------Builder------------>
    @Builder
    public Member(MemberRequestDto.PostMemberDetail memberDetail) {

        this.memberNickname = memberDetail.getMemberNickname();
        this.pwd = new BCryptPasswordEncoder().encode(memberDetail.getPwd1());
        this.memberAge = memberDetail.getMemberAge();
        this.memberSex = memberDetail.getMemberSex();
        this.diseaseList = memberDetail.getDiseaseList();
    }
}
