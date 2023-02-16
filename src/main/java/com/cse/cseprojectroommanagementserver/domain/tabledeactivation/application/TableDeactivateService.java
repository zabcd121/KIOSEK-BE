package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.application;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository.ProjectTableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReservationCancelService;
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

import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationRequestDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TableDeactivateService {

    private final ReservationCancelService reservationCancelService;
    private final ProjectTableRepository projectTableRepository;
    private final TableDeactivationRepository tableDeactivationRepository;
    private final TableDeactivationSearchableRepository tableDeactivationSearchableRepository;

    /**
     * Todo: 중복 비활성화 방지하기 -> 중복된 비활성화 있으면 그냥 전체 에러내자!  -> 예약하기에서 비활성화 체크도 해야함., 해당 테이블과 그 시간에 존재하는 예약 삭제해야함
     * @param tableDeactivationRequest
     */
    @Transactional
    public void deactivateTables(TableDeactivationRequest tableDeactivationRequest) {
        List<TableDeactivation> tableDeactivationList = getTableDeactivationList(tableDeactivationRequest);
        reservationCancelService.cancelExistsReservationListWithTableDeactivation(tableDeactivationRequest);
        tableDeactivationRepository.saveAll(tableDeactivationList);
    }

    private List<TableDeactivation> getTableDeactivationList(TableDeactivationRequest tableDeactivationRequest) {
        TableDeactivationInfo info = tableDeactivationRequest.getTableDeactivationInfoRequest().toEntity();

        List<TableDeactivation> tableDeactivationList = new ArrayList<>();

        for (Long tableId : tableDeactivationRequest.getProjectTableIdList()) {
            if(!isDuplicatedDeactivation(tableId, info.getStartAt(), info.getEndAt())) {
                tableDeactivationList.add(
                        TableDeactivation.createTableDeactivation(projectTableRepository.getReferenceById(tableId), info)
                );
            }
        }

        return tableDeactivationList;
    }

    private boolean isDuplicatedDeactivation(Long tableId, LocalDateTime startAt, LocalDateTime endAt) {
        if(tableDeactivationSearchableRepository.existsBy(tableId, startAt, endAt)) {
            throw new DuplicatedDeactivationException();
        }

        return false;
    }

}
