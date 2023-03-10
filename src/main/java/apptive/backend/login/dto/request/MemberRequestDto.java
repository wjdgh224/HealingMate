package apptive.backend.login.dto.request;

import apptive.backend.validation.login.Password;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class MemberRequestDto {

    //<------------POST------------>
    @Getter
    @Setter
    public static class PostMemberDetail {

        private String memberNickname;
        private String pwd1;
        private String pwd2;

        private String memberAge;
        private String memberSex;
        private List<String> diseaseList;
    }

    @Getter @Setter
    public static class PostMemberValidationCheck {

        private String memberNickname;
        @Password //비밀번호 관련 validation 확인
        private String pwd1;
        private String pwd2;
    }
}
