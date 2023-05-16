package com.cse.cseprojectroommanagementserver.unittest.domain.tabledeactivation.application;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository.ProjectTableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationUpdatableRepository;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.application.TableDeactivateService;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivation;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivationInfo;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationRepository;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationSearchableRepository;
import com.cse.cseprojectroommanagementserver.global.error.exception.DuplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;
import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationReqDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TableDeactivateServiceUnitTest {

    @InjectMocks
    TableDeactivateService tableDeactivateService;

    @Mock
    TableDeactivationRepository tableDeactivationRepository;
    @Mock
    TableDeactivationSearchableRepository tableDeactivationSearchableRepository;
    @Mock
    ReservationUpdatableRepository reservationUpdatableRepository;
    @Mock
    ProjectTableRepository projectTableRepository;

    TableDeactivationReq tableDeactivationReq;

    @BeforeEach
    void setUp() {
        List tableList = new ArrayList<>();
        tableList.add(1L);
        tableList.add(2L);
        tableDeactivationReq = TableDeactivationReq.builder()
                .projectRoomId(1L)
                .projectTableIdList(tableList)
                .tableDeactivationInfoReq(
                        TableDeactivationInfoReq.builder()
                                .reason("특식 배부")
                                .startAt(LocalDateTime.now().plusDays(2))
                                .endAt(LocalDateTime.now().plusDays(2).plusHours(3))
                                .build())
                .build();
    }

    /**
     * 테스트 케이스
     * C1. 테이블 비활성화 성공
     * C2. 테이블 비활성화 실패
     * C2-01. 테이블 비활성화 실패 - 중복된 비활성화 요청
     */

    @Test
    @DisplayName("C1. 테이블 비활성화 성공")
    void 테이블비활성화_성공() {
        // Given
        given(tableDeactivationSearchableRepository.existsBy(
                anyLong(), eq(tableDeactivationReq.getTableDeactivationInfoReq().getStartAt()), eq(tableDeactivationReq.getTableDeactivationInfoReq().getEndAt()))
        ).willReturn(false);

        given(projectTableRepository.getReferenceById(tableDeactivationReq.getProjectTableIdList().get(0))).willReturn(ProjectTable.builder().tableId(1L).build());
        given(projectTableRepository.getReferenceById(tableDeactivationReq.getProjectTableIdList().get(1))).willReturn(ProjectTable.builder().tableId(2L).build());

        willDoNothing().given(reservationUpdatableRepository)
                .updateStatusBy(
                        CANCELED, tableDeactivationReq.getProjectTableIdList(), tableDeactivationReq.getTableDeactivationInfoReq().getStartAt(), tableDeactivationReq.getTableDeactivationInfoReq().getEndAt()
                );

        TableDeactivationInfo info = tableDeactivationReq.getTableDeactivationInfoReq().toEntity();
        List<TableDeactivation> tableDeactivationList = new ArrayList<>();
        tableDeactivationList.add(TableDeactivation.createTableDeactivation(ProjectTable.builder().tableId(1L).build(), info));
        tableDeactivationList.add(TableDeactivation.createTableDeactivation(ProjectTable.builder().tableId(2L).build(), info));
        given(tableDeactivationRepository.saveAll(anyList())).willReturn(tableDeactivationList);

        // When
        tableDeactivateService.deactivateTables(tableDeactivationReq);

        // Then
        then(tableDeactivationRepository).should(times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("C2-01. 테이블 비활성화 실패 - 중복된 비활성화 요청")
    void 테이블비활성화_실패_중복된비활성화요청() {
        // Given
        given(tableDeactivationSearchableRepository.existsBy(
                anyLong(), eq(tableDeactivationReq.getTableDeactivationInfoReq().getStartAt()), eq(tableDeactivationReq.getTableDeactivationInfoReq().getEndAt()))
        ).willReturn(true);

        // When, Then
        assertThrows(DuplicationException.class, () -> tableDeactivateService.deactivateTables(tableDeactivationReq));
    }
}