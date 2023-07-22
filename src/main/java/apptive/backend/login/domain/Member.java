package apptive.backend.login.domain;

import apptive.backend.config.StringListConverter;
import apptive.backend.login.dto.request.MemberRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
public class Member implements Serializable, UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    @Getter //가입 후 멤버아이디를 전달하기 위해 게터 사용
    private Long memberId;

    @Column(nullable = false)
    private String memberAge;
    @Column(nullable = false)
    private String memberSex;

    // 멤버 닉네임과 비밀번호를 통해 로그인
    @Column(nullable = false) @Getter
    private String memberNickname;
    @Column(nullable = false) @Getter
    private String pwd;

    @ElementCollection(fetch = FetchType.EAGER)
    @Getter
    private List<String> roles = new ArrayList<>();

    // 질병 추가 리스트 + nullable = true
    @Convert(converter = StringListConverter.class)
    @Column(nullable = false)
    private List<String> diseaseList;

    /*@OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Post> postList = new ArrayList<>();*/

    //<------------Builder------------>
    @Builder
    public Member(MemberRequestDto.PostMemberDetail memberDetail) {

        this.memberNickname = memberDetail.getMemberNickname();
        this.pwd = new BCryptPasswordEncoder().encode(memberDetail.getPwd1());
        this.roles = Collections.singletonList("ROLE_USER");
        this.memberAge = memberDetail.getMemberAge();
        this.memberSex = memberDetail.getMemberSex();
        this.diseaseList = memberDetail.getDiseaseList();
    }

    //<------------userDetails------------>
    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return memberNickname;
    }

    // 계정이 만료되지 않았는지 리턴 (true : 만료 안 됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않은지 리턴 (true : 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 (true : 만료 안 됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능)인지 리턴 (true : 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정이 갖고 있는 권한 목록 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
