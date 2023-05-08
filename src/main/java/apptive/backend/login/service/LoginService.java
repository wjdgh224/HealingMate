package apptive.backend.login.service;

import apptive.backend.exception.ExceptionEnum;
import apptive.backend.exception.login.LoginException;
import apptive.backend.exception.login.MemberNotExistException;
import apptive.backend.login.domain.Member;
import apptive.backend.login.dto.request.LoginRequestDto;
import apptive.backend.login.jwt.JwtTokenProvider;
import apptive.backend.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //<------------ 로그인 ------------>
    @Transactional
    public String login(LoginRequestDto.LoginDto loginDto) {

        // 로그인 시 member 찾을 수 있으면 정보 가져옴
        Member member = memberRepository.findByMemberNickname(loginDto.getMemberNickname())
                .orElseThrow(() -> new MemberNotExistException(ExceptionEnum.MEMBER_NOT_EXIST_EXCEPTION));

        // 패스워드 불일치 하면 에러 발생
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPwd())) {
            throw new LoginException(ExceptionEnum.WRONG_PWD_EXCEPTION);
        }

        // Access Token, Refresh Token 발급
        return jwtTokenProvider.createToken(member.getMemberNickname(), member.getRoles());
    }
}