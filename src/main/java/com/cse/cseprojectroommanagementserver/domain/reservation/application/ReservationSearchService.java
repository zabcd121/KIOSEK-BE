package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationSearchCondition;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationSearchableRepository;
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
import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationResDto.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationSearchService {
    private final ReservationSearchableRepository reservationSearchableRepository;
    private final TableDeactivationSearchableRepository tableDeactivationSearchableRepository;
    private final AES256 aes256;

    /**
     * TODO: decrypt에서 발생하는 예외처리 하기
     */
    public Page<SearchReservationByPagingRes> searchReservationListByConditionAndPageable(ReservationSearchCondition condition, Pageable pageable) {
        Page<SearchReservationByPagingRes> reservationListByPage = reservationSearchableRepository.findAllByConditionAndPageable(condition, pageable);

        List<SearchReservationByPagingRes> decryptedContent = decryptPersonalInfoInReservationContent(reservationListByPage.getContent());
        
        return new PageImpl<>(decryptedContent, pageable, reservationListByPage.getTotalElements());
    }

    @Timed("kiosek.reservation")
    public ReservedAndTableDeactivationInfoRes searchReservationListByProjectRoom(Long projectRoomId, FirstAndLastDateTimeReq firstAndLastDateTimeReq) {

        List<ReservationSearchRes> reservedList = reservationSearchableRepository.findAllByProjectRoomIdAndBetweenFirstAtAndLastAt(projectRoomId, firstAndLastDateTimeReq.getFirstAt(), firstAndLastDateTimeReq.getLastAt());


        List<TableDeactivationSearchRes> tableDeactivationInfo = tableDeactivationSearchableRepository.findAllByProjectRoomIdAndBetweenFirstAtAndLastAt(projectRoomId, firstAndLastDateTimeReq.getFirstAt(), firstAndLastDateTimeReq.getLastAt());


        return ReservedAndTableDeactivationInfoRes.of(reservedList, tableDeactivationInfo);
    }

    public List<CurrentReservationByMemberRes> searchMyCurrentReservationList(Long memberId) {
        return reservationSearchableRepository.findCurrentAllByMemberId(memberId);
    }

    public List<PastReservationByMemberRes> searchMyPastReservationList(Long memberId) {
        return reservationSearchableRepository.findPastAllByMemberId(memberId);
    }

    private List<SearchReservationByPagingRes> decryptPersonalInfoInReservationContent(List<SearchReservationByPagingRes> encryptedContent) {
        return encryptedContent.stream()
                .map(reservationDto -> {
                    try {
                        String decryptedLoginId = aes256.decrypt(reservationDto.getMember().getLoginId());
                        reservationDto.getMember().setLoginId(decryptedLoginId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return reservationDto;
                }).collect(Collectors.toList());
    }

}
