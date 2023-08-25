package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto;
import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.IncorrectException;
import com.cse.cseprojectroommanagementserver.global.error.exception.NotFoundException;
import com.cse.cseprojectroommanagementserver.global.error.exception.UnAuthenticatedException;
import com.cse.cseprojectroommanagementserver.global.util.aes256.AES256;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.config.RedisConfig.EV;
import static com.cse.cseprojectroommanagementserver.global.error.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberRepository memberRepository;
    private final MemberSearchableRepository memberSearchableRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final AES256 aes256;

    @Transactional
    public void changePassword(Long memberId, PasswordChangeReq passwordChangeReq) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(UNAUTHORIZED_MEMBER));

        String encodedPassword = encodePassword(passwordChangeReq.getOriginPassword());

        validatePassword(findMember.getPassword(), encodedPassword);
        findMember.changePassword(encodePassword(passwordChangeReq.getNewPassword()));
    }

    @Transactional
    public void changePasswordWithNoLogin(PasswordReissueReq passwordReissueReq) {
        Member findMember = memberSearchableRepository.findByAccountLoginId(passwordReissueReq.getLoginId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));

        checkAuthCodeIsVerified(aes256.decrypt(findMember.getEmail()));

        findMember.changePassword(encodePassword(passwordReissueReq.getPassword()));
    }

    private boolean validatePassword(String realPassword, String inputPassword) {
        if (realPassword != inputPassword) {
            throw new IncorrectException(INCORRECT_PASSWORD);
        }
        return true;
    }

    private boolean checkAuthCodeIsVerified(String email) {
        if (redisTemplate.opsForValue().get(EV + email) == null) {
            throw new UnAuthenticatedException(ErrorCode.UNAUTHENTICATED_AUTH_CODE);
        }
        redisTemplate.delete(EV + email);
        return true;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
