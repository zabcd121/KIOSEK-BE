package com.cse.cseprojectroommanagementserver.domain.member.dto;

import lombok.*;

public class TokenDto {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class TokensDto {
        private String accessToken;
        private String refreshToken;

        public static TokensDto of(String accessToken, String refreshToken) {
            return new TokensDto(accessToken, refreshToken);
        }
    }
}
