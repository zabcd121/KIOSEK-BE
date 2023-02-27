package com.cse.cseprojectroommanagementserver.unittest.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberSignupValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    @DisplayName("사용자가 정상적인 아이디, 비밀번호, 이메일, 이름을 입력한 경우 정상적으로 유효성 검사에 통과한다.")
    void isValidSignupInfo() {
        SignupReq signupDto = SignupReq.builder()
                .loginId("20180335")
                .password("example1234!")
                .email("example@kumoh.ac.kr")
                .name("소공이")
                .build();

        Set<ConstraintViolation<SignupReq>> violations = validator.validate(signupDto);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("사용자가 아이디(학번) 형식이 옳바르지 않은 경우 유효성 검사에 실패한다.")
    void isInvalidLoginId() {
        SignupReq signupDto = SignupReq.builder()
                .loginId("abc123")
                .password("example1234!")
                .email("example@kumoh.ac.kr")
                .name("소공이")
                .build();

        Set<ConstraintViolation<SignupReq>> violations = validator.validate(signupDto);
        assertEquals("8자리 학번 형식에 맞게 입력해야 합니다. ex)20230001", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("사용자의 패스워드 형식이 옳바르지 않은 경우 유효성 검사에 실패한다.")
    void isInvalidPassword() {
        SignupReq signupDto = SignupReq.builder()
                .loginId("20180335")
                .password("example123")
                .email("example@kumoh.ac.kr")
                .name("소공이")
                .build();

        Set<ConstraintViolation<SignupReq>> violations = validator.validate(signupDto);
        assertEquals("최소 한개 이상의 대문자 또는 소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("사용자의 이메일 형식이 옳바르지 않은 경우 유효성 검사에 실패한다.")
    void isInvalidEmail() {
        SignupReq signupDto = SignupReq.builder()
                .loginId("20180335")
                .password("example1234!")
                .email("examplekumoh")
                .name("소공이")
                .build();

        Set<ConstraintViolation<SignupReq>> violations = validator.validate(signupDto);
        assertEquals("유효하지 않은 이메일 형식입니다.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("사용자의 이름 형식이 옳바르지 않은 경우 유효성 검사에 실패한다.")
    void isInvalidName() {
        SignupReq signupDto = SignupReq.builder()
                .loginId("20180335")
                .password("example1234!")
                .email("example@kumoh.ac.kr")
                .name("!소공이")
                .build();

        Set<ConstraintViolation<SignupReq>> violations = validator.validate(signupDto);
        assertEquals("이름에는 한글, 영어만 입력 가능합니다.", violations.iterator().next().getMessage());
    }
}
