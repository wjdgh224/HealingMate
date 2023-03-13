package apptive.backend.login.service;

import apptive.backend.exception.ExceptionEnum;
import apptive.backend.exception.login.LoginException;
import apptive.backend.exception.login.MemberNotExistException;
import apptive.backend.login.domain.Member;
import apptive.backend.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // <-------------------- ? -------------------->
    public Member loadMemberByNickname(String memberNickname) throws MemberNotExistException {

        Optional<Member> member = memberRepository.findByMemberNickname(memberNickname);
        if(member.isEmpty()) {

            throw new MemberNotExistException(ExceptionEnum.MEMBER_NOT_EXIST_EXCEPTION);
        }

        return member.get();
    }

    // <-------------------- auth part -------------------->
    public Member authenticate(String memberNickname, String password) {

        Optional<Member> optionalMember = memberRepository.findByMemberNickname(memberNickname);

        if(optionalMember.isPresent()) {

            Member member = optionalMember.get();
            if(passwordEncoder.matches(password, member.getPwd())) {

            } else {
                throw new LoginException(ExceptionEnum.WRONG_PWD_EXCEPTION);
            }
        } else {
            throw new MemberNotExistException(ExceptionEnum.MEMBER_NOT_EXIST_EXCEPTION);
        }

        return optionalMember.get();
    }
}
