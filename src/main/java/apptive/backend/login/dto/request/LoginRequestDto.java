package apptive.backend.login.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotNull
    private String memberNickname;

    @NotNull
    private String password;
}
