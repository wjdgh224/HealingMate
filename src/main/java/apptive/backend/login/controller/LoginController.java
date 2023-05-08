package apptive.backend.login.controller;

import apptive.backend.login.dto.request.LoginRequestDto;
import apptive.backend.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto.LoginDto loginRequestDto) {
        return new ResponseEntity(loginService.login(loginRequestDto), HttpStatus.OK);
    }
}
