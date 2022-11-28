package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.SignupRepository;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import com.cse.cseprojectroommanagementserver.global.util.AccountQRNotCreatedException;
import com.cse.cseprojectroommanagementserver.global.util.QRGenerator;
import com.cse.cseprojectroommanagementserver.global.util.QRNotCreatedException;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberRequestDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.ACCOUNT_QR_CREATE_FAIL;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignupService {
    private final SignupRepository signupRepository;
    private final PasswordEncoder passwordEncoder;

    private final QRGenerator qrGenerator;

    @Transactional
    public void signup(SignupRequest signupDto) {
        if (!isDuplicatedLoginId(signupDto.getLoginId()) && !isDuplicatedEmail(signupDto.getEmail())) {
            try {
                Image accountQRCodeImage = qrGenerator.createAccountQRCodeImage();
                Account account = Account.builder().loginId(signupDto.getLoginId()).password(passwordEncoder.encode(signupDto.getPassword())).build();

                Member signupMember = Member.createMember(account, signupDto.getEmail(), signupDto.getName(), accountQRCodeImage);

                signupRepository.save(signupMember);
            } catch (WriterException | IOException | QRNotCreatedException e) {
                throw new AccountQRNotCreatedException(ACCOUNT_QR_CREATE_FAIL);
            }
        }
    }

    public boolean isDuplicatedLoginId(String loginId) {
        return signupRepository.existsByAccountLoginId(loginId) ? true : false;
    }

    public boolean isDuplicatedEmail(String email) {
        return signupRepository.existsByEmail(email) ? true: false;
    }
}
