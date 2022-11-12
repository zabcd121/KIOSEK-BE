package com.cse.cseprojectroommanagementserver.domain.operationpolicy.domain.model;


import java.util.List;

public class OperationPolicyCompositeVerifier implements Verifiable<Integer> {

    private List<Verifiable> operationPolicyList;

    @Override
    public void verify(Integer value) {

    }


//    @Override
//    public void verify(Integer  value) {
//        for (Verifiable verifiable : reservationPolicyList) {
//            verifiable.verify();
//        }
//    }
}
