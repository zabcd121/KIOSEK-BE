package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.SignupRepository;
import com.cse.cseprojectroommanagementserver.domain.member.exception.AccountQRNotCreatedException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.EmailDuplicatedException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.LoginIdDuplicatedException;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import com.cse.cseprojectroommanagementserver.global.util.QRGenerator;
import com.cse.cseprojectroommanagementserver.global.util.QRNotCreatedException;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignupService {
    private final SignupRepository signupRepository;
    private final PasswordEncoder passwordEncoder;

    private final QRGenerator qrGenerator;

    @Transactional
    public void signup(SignupReq signupReq) {
        if (!checkDuplicationLoginId(signupReq.getLoginId()) && !checkDuplicationEmail(signupReq.getEmail())) {
            try {
                QRImage accountQRCodeImage = qrGenerator.createAccountQRCodeImage();
                Account account = Account.builder().loginId(signupReq.getLoginId()).password(passwordEncoder.encode(signupReq.getPassword())).build();

                Member signupMember = Member.createMember(account, signupReq.getEmail(), signupReq.getName(), accountQRCodeImage);

                signupRepository.save(signupMember);
            } catch (QRNotCreatedException e) {
                throw new AccountQRNotCreatedException();
            }
        }
    }

    public boolean checkDuplicationLoginId(String loginId) {
        if(signupRepository.existsByAccountLoginId(loginId)) {
            throw new LoginIdDuplicatedException();
        }
        return false;
    }

    public boolean checkDuplicationEmail(String email) {
        if(signupRepository.existsByEmail(email)) {
            throw new EmailDuplicatedException();
        }
        return false;
    }
}
