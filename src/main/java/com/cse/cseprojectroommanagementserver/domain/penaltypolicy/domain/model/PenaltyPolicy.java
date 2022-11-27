package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model;

import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import com.cse.cseprojectroommanagementserver.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PenaltyPolicy extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long penaltyPolicyId;

    private Integer violationCountToImposePenalty;
    private Integer numberOfSuspensionDay;

    @Enumerated(EnumType.STRING)
    private AppliedStatus appliedStatus;

}
