package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationUpdatableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.NotExistsReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservation.repository.ReservationSearchRepository;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivationInfo;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;
import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationRequestDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationCancelService {
    private final ReservationSearchableRepository reservationSearchableRepository;
    private final ReservationUpdatableRepository reservationUpdatableRepository;

    @Transactional
    public void cancelReservationByMember(Long reservationId) {
        Reservation findReservation = reservationSearchableRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new NotExistsReservationException());
        findReservation.cancel();
    }

    @Transactional
    public void cancelExistsReservationListWithTableDeactivation(TableDeactivationRequest tableDeactivationRequest) {
        List<Long> projectTableIdList = tableDeactivationRequest.getProjectTableIdList();
        TableDeactivationInfo tableDeactivationInfo = tableDeactivationRequest.getTableDeactivationInfoRequest().toEntity();
        reservationUpdatableRepository.updateStatusBy(CANCELED, projectTableIdList, tableDeactivationInfo.getStartAt(), tableDeactivationInfo.getEndAt());
    }
}
