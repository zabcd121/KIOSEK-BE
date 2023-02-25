package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.application;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.NumberOfSuspensionDay;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.ViolationCountToImposePenalty;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.repository.PenaltyPolicyRepository;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyReqDto;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.exception.NotExistsPenaltyPolicyException;
import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyReqDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PenaltyPolicyChangeServiceMockTest {

    @InjectMocks
    PenaltyPolicyChangeService penaltyPolicyChangeService;
    @Mock
    PenaltyPolicyRepository penaltyPolicyRepository;

    PenaltyPolicyChangeReq changeReq;

    @BeforeEach
    void setUp() {
        changeReq = PenaltyPolicyChangeReq.builder().penaltyPolicyId(1L).violationCountToImposePenalty(5).numberOfSuspensionDay(4).build();
    }

    /**
     * C1. 제재 정책 변경 성공
     * C2. 제재 정책 변경 실패
     * C2-01. 제재 정책 변경 실패 - 해당 정책이 존재하지 않음
     */

    @Test
    @DisplayName("C1. 제재 정책 변경 성공")
    void 제재정책변경_성공() {
        // Given
        PenaltyPolicy existingPenaltyPolicy = PenaltyPolicy.builder().penaltyPolicyId(1L)
                .violationCountToImposePenalty(new ViolationCountToImposePenalty(5))
                .numberOfSuspensionDay(new NumberOfSuspensionDay(4))
                .appliedStatus(AppliedStatus.CURRENTLY).build();
        given(penaltyPolicyRepository.findById(changeReq.getPenaltyPolicyId())).willReturn(Optional.of(existingPenaltyPolicy));

        PenaltyPolicy newPenaltyPolicy = PenaltyPolicy.createPenaltyPolicy(changeReq.getViolationCountToImposePenalty(), changeReq.getNumberOfSuspensionDay());
        given(penaltyPolicyRepository.save(any())).willReturn(newPenaltyPolicy);

        // When
        penaltyPolicyChangeService.changePenaltyPolicy(changeReq);

        // Then
        assertEquals(AppliedStatus.DEPRECATED, existingPenaltyPolicy.getAppliedStatus());
        assertEquals(AppliedStatus.CURRENTLY, newPenaltyPolicy.getAppliedStatus());

    }

    @Test
    @DisplayName("C2-01. 제재 정책 변경 실패 - 해당 정책이 존재하지 않음")
    void 제재정책변경_실패_해당정책존재X() {
        // Given
        given(penaltyPolicyRepository.findById(changeReq.getPenaltyPolicyId())).willReturn(Optional.ofNullable(null));

        // When, Then
        assertThrows(NotExistsPenaltyPolicyException.class, () -> penaltyPolicyChangeService.changePenaltyPolicy(changeReq));
    }
}