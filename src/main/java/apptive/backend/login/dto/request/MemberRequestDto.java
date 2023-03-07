package apptive.backend.login.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class MemberRequestDto {

    //<------------POST------------>
    @Getter
    @Setter
    public static class PostMemberDetail {

        private String memberName;
        private String memberNickname;
        private String pwd1;
        private String pwd2;

        private LocalDate memberBirth;
        private String memberSex;
        private List<String> diseaseList;
    }
}
