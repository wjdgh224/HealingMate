package apptive.backend.login.controller;

import apptive.backend.exception.ExceptionEnum;
import apptive.backend.exception.login.LoginException;
import apptive.backend.login.dto.request.MemberRequestDto;
import apptive.backend.login.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
public class MemberController {

    private final MemberService memberService;

    // <-------------------- POST part -------------------->
    @PostMapping("/join/1")
    public ResponseEntity memberStep1(@RequestPart(name = "validationCheck") @Valid MemberRequestDto.PostMemberValidationCheck validationCheck) {

        // 1단계 - 닉네임과 비밀번호 조건 체크
        memberService.checkMemberCondition(validationCheck);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/join/2")
    public ResponseEntity memberStep2(@RequestPart(name = "memberDetail") @Valid MemberRequestDto.PostMemberDetail memberDetail) {

        // 2단계 - 닉네임, 비밀번호, 성별, 나이, 관심질환 저장
        Long memberId = memberService.createMember(memberDetail);

        return new ResponseEntity(memberId, HttpStatus.CREATED);
    }
}
