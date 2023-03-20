package apptive.backend.login.controller;

import apptive.backend.login.dto.request.LoginRequestDto;
import apptive.backend.login.service.LoginService;
import apptive.backend.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) {
        return new ResponseEntity(memberService.login(loginRequestDto), HttpStatus.OK);
    }

}
