package apptive.backend.login.controller;

import apptive.backend.login.dto.request.MemberRequestDto;
import apptive.backend.login.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
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

    // <-------------------- DELETE part -------------------->
    @DeleteMapping("/delete")
    public ResponseEntity deleteMember(@RequestPart(name = "memberId")Long memberId,
                                       HttpServletRequest httpServletRequest) {

        memberService.deleteMember(memberId);

        //저장된 세션 삭제
        HttpSession session = httpServletRequest.getSession();
        session.invalidate();

        return new ResponseEntity("회원탈퇴가 완료되었습니다.", HttpStatus.OK);
    }
}
