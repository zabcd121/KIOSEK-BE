package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationSearchCondition;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.IsNotInUseTableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ReservationSearchableRepository reservationSearchableRepository;

    public Page<SearchReservationByPagingResponse> searchReservationListByConditionAndPageable(ReservationSearchCondition condition, Pageable pageable) {
        return reservationSearchableRepository.findAllByConditionAndPageable(condition, pageable);
    }

    public List<SearchReservationResponse> searchReservationListByProjectRoom(Long projectRoomId, FirstAndLastDateTimeRequest firstAndLastDateTimeRequest) {

        log.info("getFirstDateTime: {}", firstAndLastDateTimeRequest.getFirstDateTime());
        log.info("getLastDateTime: {}", firstAndLastDateTimeRequest.getLastDateTime());

        return reservationSearchableRepository.findAllByProjectRoomIdAndBetweenFirstDateTimeAndLastDateTime(
                projectRoomId,
                firstAndLastDateTimeRequest.getFirstDateTime(),
                firstAndLastDateTimeRequest.getLastDateTime()
        );

    }


    public List<CurrentReservationByMemberResponse> searchMyCurrentReservationList(Long memberId) {
        return reservationSearchableRepository.findCurrentAllByMemberId(memberId);
    }

    public List<PastReservationByMemberResponse> searchMyPastReservationList(Long memberId) {
        return reservationSearchableRepository.findPastAllByMemberId(memberId);
    }

    public boolean checkIsInUseTableAtCurrent(String tableName) {
        if(!reservationSearchableRepository.existsCurrentInUseReservationByTableName(tableName)){
            throw new IsNotInUseTableException();
        }
        return true;
    }


}
