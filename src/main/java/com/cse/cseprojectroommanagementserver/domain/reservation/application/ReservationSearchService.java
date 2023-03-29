package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationSearchCondition;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.IsNotInUseTableException;
import com.cse.cseprojectroommanagementserver.global.util.AES256;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResDto.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationSearchService {
    private final ReservationSearchableRepository reservationSearchableRepository;
    private final AES256 aes256;

    public Page<SearchReservationByPagingRes> searchReservationListByConditionAndPageable(ReservationSearchCondition condition, Pageable pageable) {
        Page<SearchReservationByPagingRes> reservationListByPage = reservationSearchableRepository.findAllByConditionAndPageable(condition, pageable);
        List<SearchReservationByPagingRes> content = reservationListByPage.getContent();
        List<SearchReservationByPagingRes> decryptedContent = content.stream()
                .map(reservation -> {
                    String decryptedLoginId = null;
                    try {
                        decryptedLoginId = aes256.decrypt(reservation.getMember().getLoginId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    reservation.getMember().setLoginId(decryptedLoginId);
                    return reservation;
                }).collect(Collectors.toList());

        return new PageImpl<>(decryptedContent, pageable, reservationListByPage.getTotalElements());
    }

    @Timed("kiosek.reservation")
    public List<SearchReservationRes> searchReservationListByProjectRoom(Long projectRoomId, FirstAndLastDateTimeReq firstAndLastDateTimeReq) {
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
