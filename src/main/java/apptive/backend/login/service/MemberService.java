package apptive.backend.login.service;

import apptive.backend.exception.login.PwdConditionException;
import apptive.backend.exception.login.SameNickNameException;
import apptive.backend.login.domain.Member;
import apptive.backend.login.dto.request.MemberRequestDto;
import apptive.backend.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(MemberRequestDto.PostMemberDetail memberDetail) {

        /**
         * 1. 같은 닉네임을 가진 멤버가 존재하는지 확인
         * 2. 사용하고자 하는 비밀번호 2개가 같은지
         * 3. null X인 값들이 다 채워졌는지 -> 자동확인
         */

        //같은 닉네임 있는지 확인
        isUniqueMember(memberDetail.getMemberNickname());
        //비밀번호 조건 충족 여부
        checkPwdCondition(memberDetail.getPwd1(), memberDetail.getPwd2());

        Member member = Member.builder()
                        .memberDetail(memberDetail).build();

        memberRepository.save(member);
    }

    //<------------verification method------------>
    private void isUniqueMember(String memberNickname) {
        List<Long> nicknameList = memberRepository.findAllByMemberNickName(memberNickname);

        if(nicknameList.size() != 0) {
            throw new SameNickNameException();
        }
    }

    private void checkPwdCondition(String pwd1, String pwd2) {

        //1. 두 개의 비밀번호가 같은지
        if(pwd1 != pwd2) {
            throw new PwdConditionException("비밀번호가 같지 않습니다.");
        }

        //2. 비밀번호 충족 요건 만족 여부
    }
}
