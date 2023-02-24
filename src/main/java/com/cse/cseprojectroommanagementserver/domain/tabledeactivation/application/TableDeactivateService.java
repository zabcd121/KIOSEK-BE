package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.application;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository.ProjectTableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationUpdatableRepository;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivation;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivationInfo;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationRepository;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.exception.DuplicatedDeactivationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.CANCELED;
import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationReqDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TableDeactivateService {

    private final TableDeactivationRepository tableDeactivationRepository;
    private final TableDeactivationSearchableRepository tableDeactivationSearchableRepository;
    private final ReservationUpdatableRepository reservationUpdatableRepository;
    private final ProjectTableRepository projectTableRepository;
    /**
     * Todo: 중복 비활성화 방지하기 -> 중복된 비활성화 있으면 그냥 전체 에러내자!  -> 예약하기에서 비활성화 체크도 해야함., 해당 테이블과 그 시간에 존재하는 예약 삭제해야함
     * @param tableDeactivationReq
     */
    @Transactional
    public void deactivateTables(TableDeactivationReq tableDeactivationReq) {
        List<TableDeactivation> tableDeactivationList = getTableDeactivationList(tableDeactivationReq);
        cancelExistsReservationListWithTableDeactivation(tableDeactivationReq);
        tableDeactivationRepository.saveAll(tableDeactivationList);
    }

    private List<TableDeactivation> getTableDeactivationList(TableDeactivationReq tableDeactivationReq) {
        TableDeactivationInfo info = tableDeactivationReq.getTableDeactivationInfoReq().toEntity();

        List<TableDeactivation> tableDeactivationList = new ArrayList<>();

        for (Long tableId : tableDeactivationReq.getProjectTableIdList()) {
            if(!isDuplicatedDeactivation(tableId, info.getStartAt(), info.getEndAt())) {
                tableDeactivationList.add(
                        TableDeactivation.createTableDeactivation(projectTableRepository.getReferenceById(tableId), info)
                );
            }
        }

        return tableDeactivationList;
    }

    private void cancelExistsReservationListWithTableDeactivation(TableDeactivationReq tableDeactivationReq) {
        List<Long> projectTableIdList = tableDeactivationReq.getProjectTableIdList();
        TableDeactivationInfo tableDeactivationInfo = tableDeactivationReq.getTableDeactivationInfoReq().toEntity();
        reservationUpdatableRepository.updateStatusBy(CANCELED, projectTableIdList, tableDeactivationInfo.getStartAt(), tableDeactivationInfo.getEndAt());
    }

    private boolean isDuplicatedDeactivation(Long tableId, LocalDateTime startAt, LocalDateTime endAt) {
        if(tableDeactivationSearchableRepository.existsBy(tableId, startAt, endAt)) {
            throw new DuplicatedDeactivationException();
        }

        return false;
    }


}
