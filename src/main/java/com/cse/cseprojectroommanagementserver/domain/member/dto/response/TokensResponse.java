package com.cse.cseprojectroommanagementserver.domain.member.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokensResponse {
    private String accessToken;
    private String refreshToken;

}
