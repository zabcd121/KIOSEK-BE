package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.repository.ReservationSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationRequestDto.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResponseDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchReservationService {
    private final ReservationSearchRepository reservationSearchRepository;

    public List<SearchReservationResponse> searchReservationListByProjectRoom(Long projectRoomId, FirstAndLastDateTimeRequest firstAndLastDateTimeRequest) {
        return reservationSearchRepository.findAllByProjectRoomIdAndBetweenFirstDateTimeAndLastDateTime(
                projectRoomId,
                firstAndLastDateTimeRequest.getFirstDateTime(),
                firstAndLastDateTimeRequest.getLastDateTime()
        );
    }

    public List<CurrentReservationByMemberResponse> searchCurrentReservationListByMember(Long memberId) {
        return reservationSearchRepository.findCurrentAllByMemberId(memberId);
    }


}
