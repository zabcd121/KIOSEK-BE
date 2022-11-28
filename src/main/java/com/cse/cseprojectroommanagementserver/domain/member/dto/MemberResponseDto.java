package com.cse.cseprojectroommanagementserver.domain.member.dto;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import lombok.*;

public class MemberResponseDto {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class LoginResponse {

        private TokensDto tokenInfo;
        private LoginMemberInfoResponse memberInfo;


    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class LoginMemberInfoResponse {

        private Long memberId;
        private String name;
        private RoleType roleType;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class TokensDto {
        private String accessToken;
        private String refreshToken;

    }
}
