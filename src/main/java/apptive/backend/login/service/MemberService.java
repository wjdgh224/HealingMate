package apptive.backend.login.service;

import apptive.backend.exception.ExceptionEnum;
import apptive.backend.exception.login.PwdConditionException;
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
    public Long createMember(MemberRequestDto.PostMemberDetail memberDetail) {

        Member member = Member.builder()
                        .memberDetail(memberDetail).build();

        memberRepository.save(member);

        return member.getMemberId();
    }

    public void checkMemberCondition(MemberRequestDto.PostMemberValidationCheck validationCheck) {

        /**
         * 1. 닉네임 중복 확인
         * 2. 영어 대/소문자 + 특수문자 + 8자이상 -> annotation 으로 체크
         * 3. 사용하고자 하는 비밀번호 2개가 같은지
         */

        //닉네임 중복 확인
        //isUniqueMember(memberNickname);
        //비밀번호 조건 충족 여부
        checkPwdCondition(validationCheck.getPwd1(), validationCheck.getPwd2());
    }

    //<------------verification method------------>
    private void isUniqueMember(String memberNickname) {
        List<Long> nicknameList = memberRepository.findAllByMemberNickName(memberNickname);

        if(nicknameList.size() != 0) {
            //throw new SameNickNameException();
        }
    }

    private void checkPwdCondition(String pwd1, String pwd2) {

        //1. 두 개의 비밀번호가 같은지
        if(pwd1.equals(pwd2)) {

        } else {
            throw new PwdConditionException(ExceptionEnum.PWD_CONDITION_EXCEPTION);
        }
    }
}
