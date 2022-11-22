package com.cse.cseprojectroommanagementserver.domain.operationpolicy.domain.model;

import com.cse.cseprojectroommanagementserver.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatableOperationPolicy extends BaseEntity implements Verifiable<Integer> {

    @Id @GeneratedValue
    private Long operationPolicyId;

    @Enumerated(value = EnumType.STRING)
    private UpdatableOperationPolicyType operationPolicyType;

    private Integer setValue;

    @Enumerated(value = EnumType.STRING)
    private AppliedStatus appliedStatus;

    @Override
    public void verify(Integer value) {

    }
}
