package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.member.dto.request.LoginRequest;
import com.cse.cseprojectroommanagementserver.domain.member.dto.response.LoginResponse;
import com.cse.cseprojectroommanagementserver.domain.member.dto.request.SignupRequest;
import com.cse.cseprojectroommanagementserver.domain.member.exception.DuplicatedEmailException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.DuplicatedLoginIdException;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import com.cse.cseprojectroommanagementserver.global.jwt.MemberDetailsService;
import com.cse.cseprojectroommanagementserver.global.util.AccountQRNotCreatedException;
import com.cse.cseprojectroommanagementserver.global.util.QRGenerator;
import com.cse.cseprojectroommanagementserver.global.util.QRNotCreatedException;
import com.google.zxing.WriterException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDetailsService memberDetailsService;
    private final QRGenerator qrGenerator;
    private final RedisTemplate redisTemplate;

    /**Todo
     * 1. 아이디 중복 검증
     * 2. 이메일 중복 검증
      */
    @Transactional
    public void signup(SignupRequest signupDto) {
        if(!isDuplicatedLoginId(signupDto.getLoginId()) && !isDuplicatedEmail(signupDto.getEmail())) {
            try {
                Image accountQRCodeImage = qrGenerator.createAccountQRCodeImage(signupDto.getLoginId());
                Account account = Account.builder().loginId(signupDto.getLoginId()).password(passwordEncoder.encode(signupDto.getPassword())).build();

                Member signupMember = Member.createMember(account, signupDto.getEmail(), signupDto.getName(), accountQRCodeImage);

                memberRepository.save(signupMember);
            } catch (WriterException | IOException | QRNotCreatedException e) {
                throw new AccountQRNotCreatedException(ACCOUNT_QR_CREATE_FAIL.getMessage());
            }
        }
    }

    public boolean isDuplicatedLoginId(String loginId) {
        if(memberRepository.existsByAccountLoginId(loginId)) {
            throw new DuplicatedLoginIdException(LOGIN_ID_DUPLICATED.getMessage());
        }
        return false;
    }

    public boolean isDuplicatedEmail(String email) {
        if(memberRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException(EMAIL_DUPLICATED.getMessage());
        }
        return false;
    }


}
