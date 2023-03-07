package apptive.backend.login.domain;

import apptive.backend.config.StringListConverter;
import apptive.backend.login.dto.request.MemberRequestDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long memberId;

    @Column(nullable = false)
    private String memberName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate memberBirth;
    @Column(nullable = false)
    private String memberSex;

    // 멤버 닉네임과 비밀번호를 통해 로그인
    @Column(nullable = false)
    private String memberNickName;
    @Column(nullable = false)
    private String pwd;

    // 질병 추가 리스트 + nullable = true
    @Convert(converter = StringListConverter.class)
    private List<String> diseaseList;

    //<------------Builder------------>
    @Builder
    public Member(MemberRequestDto.PostMemberDetail memberDetail) {

        this.memberName = memberDetail.getMemberName();
        this.memberBirth = memberDetail.getMemberBirth();
        this.memberSex = memberDetail.getMemberSex();
        this.memberNickName = memberDetail.getMemberNickname();
        this.pwd = memberDetail.getPwd1();
        this.diseaseList = memberDetail.getDiseaseList();
    }
}
