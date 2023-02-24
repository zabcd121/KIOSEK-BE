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

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResDto.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationSearchService {
    private final ReservationSearchableRepository reservationSearchableRepository;

    public Page<SearchReservationByPagingRes> searchReservationListByConditionAndPageable(ReservationSearchCondition condition, Pageable pageable) {
        return reservationSearchableRepository.findAllByConditionAndPageable(condition, pageable);
    }

    public List<SearchReservationRes> searchReservationListByProjectRoom(Long projectRoomId, FirstAndLastDateTimeReq firstAndLastDateTimeReq) {

        log.info("getFirstDateTime: {}", firstAndLastDateTimeReq.getFirstAt());
        log.info("getLastDateTime: {}", firstAndLastDateTimeReq.getLastAt());

        return reservationSearchableRepository.findAllByProjectRoomIdAndBetweenFirstDateTimeAndLastDateTime(
                projectRoomId,
                firstAndLastDateTimeReq.getFirstAt(),
                firstAndLastDateTimeReq.getLastAt()
        );

    }


    public List<CurrentReservationByMemberRes> searchMyCurrentReservationList(Long memberId) {
        return reservationSearchableRepository.findCurrentAllByMemberId(memberId);
    }

    public List<PastReservationByMemberRes> searchMyPastReservationList(Long memberId) {
        return reservationSearchableRepository.findPastAllByMemberId(memberId);
    }

    public boolean checkIsInUseTable(String tableName) {
        if(!reservationSearchableRepository.existsCurrentInUseReservationByTableName(tableName)){
            throw new IsNotInUseTableException();
        }
        return true;
    }

}
