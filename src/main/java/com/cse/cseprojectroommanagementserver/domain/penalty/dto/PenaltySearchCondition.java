package com.cse.cseprojectroommanagementserver.domain.penalty.dto;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter @Setter
public class PenaltySearchCondition {
    private String memberName;
    private String loginId;
}
