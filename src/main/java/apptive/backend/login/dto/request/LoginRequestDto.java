package apptive.backend.login.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public class LoginRequestDto {

    @Getter
    public static class LoginDto {
        @NotNull
        private String memberNickname;

        @NotNull
        private String password;
    }

    @Getter
    public static class TokenDto {

        private String accessToken;
        private String refreshToken;

        public TokenDto(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
