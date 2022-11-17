package com.cse.cseprojectroommanagementserver.domain.member.dto.response;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {

    private TokensResponse tokenInfo;
    private LoginMemberInfoResponse memberInfo;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public class LoginMemberInfoResponse {

        private Long memberId;
        private String name;
        private RoleType roleType;
    }
}
