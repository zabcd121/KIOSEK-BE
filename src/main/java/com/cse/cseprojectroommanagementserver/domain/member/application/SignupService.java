package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.SignupRepository;
import com.cse.cseprojectroommanagementserver.domain.member.exception.NotYetVerifiedAuthCodeException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.DuplicatedEmailException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.DuplicatedLoginIdException;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import com.cse.cseprojectroommanagementserver.global.util.aes256.AES256;
import com.cse.cseprojectroommanagementserver.global.util.qrgenerator.QRGenerator;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.config.RedisConfig.EV;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignupService {
    private final SignupRepository signupRepository;
    private final RedisTemplate redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final QRGenerator qrGenerator;
    private final AES256 aes256;

    @Timed("kiosek.member")
    @Transactional
    public void signup(SignupReq signupReq) {
        if (!checkDuplicationLoginId(signupReq.getLoginId())
                && !checkDuplicationEmail(signupReq.getEmail())
                && checkAuthCodeIsVerified(signupReq.getEmail())) {

            QRImage accountQRCodeImage = qrGenerator.createAccountQRCodeImage();

            Account account = Account.builder()
                    .loginId((aes256.encrypt(signupReq.getLoginId())))
                    .password(passwordEncoder.encode(signupReq.getPassword())).build();

            Member signupMember = Member.createMember(account,
                    aes256.encrypt(signupReq.getEmail()), signupReq.getName(), accountQRCodeImage);

            signupRepository.save(signupMember);
        }
    }

    public boolean checkAuthCodeIsVerified(String email) {
        if (redisTemplate.opsForValue().get(EV + email) == null) {
            throw new NotYetVerifiedAuthCodeException();
        }
        redisTemplate.delete(EV + email);
        return true;
    }

    public boolean checkDuplicationLoginId(String loginId) {
        if (signupRepository.existsByAccountLoginId(aes256.encrypt(loginId))) {
            throw new DuplicatedLoginIdException();
        }
        return false;
    }

    public boolean checkDuplicationEmail(String email) {
        if (signupRepository.existsByEmail(aes256.encrypt(email))) {
            throw new DuplicatedEmailException();
        }
        return false;
    }
}
