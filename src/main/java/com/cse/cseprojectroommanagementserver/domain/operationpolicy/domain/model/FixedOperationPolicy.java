package com.cse.cseprojectroommanagementserver.domain.operationpolicy.domain.model;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class FixedOperationPolicy implements Verifiable<Integer> {

    private FixedOperationPolicyType operationPolicyType;

    private Integer limitSetValue;

    @Override
    public void verify(Integer value) {

    }
}
