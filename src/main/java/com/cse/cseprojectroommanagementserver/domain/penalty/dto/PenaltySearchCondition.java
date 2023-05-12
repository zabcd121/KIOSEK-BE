package com.cse.cseprojectroommanagementserver.domain.penalty.dto;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PenaltySearchCondition {
    private String memberName;
    private String loginId;
}
