package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.exception.IsNotInUseTableException;
import com.cse.cseprojectroommanagementserver.domain.reservation.repository.ReservationSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationRequestDto.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResponseDto.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationSearchService {
    private final ReservationSearchRepository reservationSearchRepository;

    public List<SearchReservationResponse> searchReservationListByProjectRoom(Long projectRoomId, FirstAndLastDateTimeRequest firstAndLastDateTimeRequest) {

        log.info("getFirstDateTime: {}", firstAndLastDateTimeRequest.getFirstDateTime());
        log.info("getLastDateTime: {}", firstAndLastDateTimeRequest.getLastDateTime());

        return reservationSearchRepository.findAllByProjectRoomIdAndBetweenFirstDateTimeAndLastDateTime(
                projectRoomId,
                firstAndLastDateTimeRequest.getFirstDateTime(),
                firstAndLastDateTimeRequest.getLastDateTime()
        );

    }


    public List<CurrentReservationByMemberResponse> searchMyCurrentReservationList(Long memberId) {
        return reservationSearchRepository.findCurrentAllByMemberId(memberId);
    }

    public List<PastReservationByMemberResponse> searchMyPastReservationList(Long memberId) {
        return reservationSearchRepository.findPastAllByMemberId(memberId);
    }

    public boolean checkIsInUseTableAtCurrent(String tableName) {
        if(!reservationSearchRepository.existsCurrentInUseReservationByTableName(tableName)){
            throw new IsNotInUseTableException();
        }
        return true;
    }


}
