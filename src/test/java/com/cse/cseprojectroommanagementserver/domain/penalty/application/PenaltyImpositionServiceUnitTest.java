package com.cse.cseprojectroommanagementserver.domain.penalty.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltyRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.exception.ImpossibleExtensionReqException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltyReqDto.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PenaltyImpositionServiceUnitTest {

    @InjectMocks PenaltyImpositionService penaltyImpositionService;

    @Mock PenaltyRepository penaltyRepository;
    @Mock PenaltySearchableRepository penaltySearchableRepository;
    @Mock MemberRepository memberRepository;

    PenaltyImpositionReq penaltyImpositionReq;

    @BeforeEach
    void setUp() {
        penaltyImpositionReq = PenaltyImpositionReq.builder().memberId(1L).description("책상 훼손").startDt(LocalDate.now()).endDt(LocalDate.now().plusDays(3)).build();
    }

    /** 테스트 케이스
     * C1. 제재 성공
         * C1-01. 제재 성공 - 기존에 제재중인 회원 아님
         * C1-02. 제재 성공 - 현재 제재중인 회원일 경우 새로운 요청의 제재 종료일이 현재 종료일보다 더 미래인 경우 제재 종료일을 연장
     * C2. 제재 실패
         * C2-01. 제재 실패 - 현재 제재중인 회원일 경우 새로운 요청의 제재 종료일이 현재 종료일보다 더 과거인 경우
     */

    @Test
    @DisplayName("C1-01. 제재 성공 - 기존에 제재중인 회원 아님")
    void 제재_성공_기존에_제재중인_회원아님() {
        // Given
        given(penaltySearchableRepository.findInProgressByMemberId(penaltyImpositionReq.getMemberId())).willReturn(Optional.ofNullable(null));
        Member member = Member.builder().memberId(1L).build();
        given(memberRepository.getReferenceById(penaltyImpositionReq.getMemberId())).willReturn(member);
        Penalty savedPenalty = Penalty.builder().penaltyId(1L).description("미사용 2회").member(member).build();
        given(penaltyRepository.save(any())).willReturn(savedPenalty);

        // When
        penaltyImpositionService.imposePenalty(penaltyImpositionReq);

        // Then
        then(penaltyRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("C1-02. 제재 성공 - 현재 제재중인 회원이어서 새로운 요청의 제재 종료일이 현재 종료일보다 더 미래인 경우 제재 종료일을 연장")
    void 제재_성공_기존에제재중인회원_새로운종료일이기존보다미래인경우() {
        // Given
        Penalty inProgressPenalty = Penalty.builder().penaltyId(1L).description("미사용 2회").member(Member.builder().memberId(1L).build())
                .startDt(LocalDate.now()).endDt(LocalDate.now().plusDays(2)).build();
        given(penaltySearchableRepository.findInProgressByMemberId(penaltyImpositionReq.getMemberId())).willReturn(Optional.of(inProgressPenalty));

        // When
        penaltyImpositionService.imposePenalty(penaltyImpositionReq);

        // Then
        Assertions.assertEquals(LocalDate.now().plusDays(3), inProgressPenalty.getEndDt());
    }

    @Test
    @DisplayName("C2-01. 제재 실패 - 현재 제재중인 회원일 경우 새로운 요청의 제재 종료일이 현재 종료일보다 더 과거인 경우")
    void 제재_성공_기존에제재중인회원_새로운종료일이기존보다과거인경우() {
        // Given
        Penalty inProgressPenalty = Penalty.builder().penaltyId(1L).description("미사용 2회").member(Member.builder().memberId(1L).build())
                .startDt(LocalDate.now()).endDt(LocalDate.now().plusDays(4)).build();
        given(penaltySearchableRepository.findInProgressByMemberId(penaltyImpositionReq.getMemberId())).willReturn(Optional.of(inProgressPenalty));

        // When, Then
        Assertions.assertThrows(ImpossibleExtensionReqException.class, () -> penaltyImpositionService.imposePenalty(penaltyImpositionReq));
    }
}