package com.macro.hjstore.dto.user;

import com.macro.hjstore.model.user.User;
import com.macro.hjstore.model.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UserRequest {

    @Getter
    @Setter
    public static class LoginInDTO{
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @NotEmpty
        @Size(min = 4, max = 20)
        private String password;

    }

    @Getter
    @Setter
    public static class JoinInDTO{
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @NotEmpty
        @Size(min = 4, max = 20)
        private String password;

        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~20자 이내로 작성해주세요")
        private String username;

        @NotEmpty
        @Size(min = 10, max = 10, message = "10자리로 입력해주세요")
        private String birth;

        public User toEntity(){
            return User.builder()
                    .email(email)
                    .password(password)
                    .username(username)
                    .birth(birth)
                    .build();
        }
    }
}
