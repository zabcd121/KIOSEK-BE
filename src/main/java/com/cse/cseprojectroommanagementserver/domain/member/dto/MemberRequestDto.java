package com.cse.cseprojectroommanagementserver.domain.member.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberRequestDto {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class LoginRequest {

        @NotBlank(message = "아이디를 입력해주세요.")
        private String loginId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class SignupRequest {

        @NotBlank(message = "학번을 입력해주세요.")
        @Pattern(message = "8자리 학번 형식에 맞게 입력해야 합니다. ex)20230001",
                regexp = "^[0-9]{8}")
        private String loginId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        /*@Pattern(message = "최소 한개 이상의 대문자 또는 소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다.",
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#!~$%^&-+=()])(?=\\S+$).{8,16}$")*/
        private String password;

        @NotBlank(message = "이름을 입력해주세요.")
        @Pattern(message = "이름에는 한글, 영어만 입력 가능합니다.",
                regexp = "^[가-힣a-zA-Z]*$")
        private String name;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "유효하지 않은 이메일 형식입니다.",
                regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
        private String email;

    }
}
