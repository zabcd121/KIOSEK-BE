package com.cse.cseprojectroommanagementserver.domain.member.dto;

import lombok.*;

public class TokenDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class TokensDto {
        private String accessToken;
        private String refreshToken;
    }
}
