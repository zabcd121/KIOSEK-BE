package com.cse.cseprojectroommanagementserver.unittest.domain.tablereturn.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.application.TableReturnService;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.TableReturn;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository.TableReturnRepository;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository.TableReturnSearchableRepository;
import com.cse.cseprojectroommanagementserver.global.dto.Image;
import com.cse.cseprojectroommanagementserver.global.error.exception.FileSystemException;
import com.cse.cseprojectroommanagementserver.global.util.fileupload.FileUploadUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TableReturnServiceUnitTest {

    @InjectMocks
    TableReturnService tableReturnService;

    @Mock TableReturnRepository tableReturnRepository;
    @Mock TableReturnSearchableRepository tableReturnSearchableRepository;
    @Mock ReservationSearchableRepository reservationSearchableRepository;
    @Mock FileUploadUtil fileUploadUtil;

    /** 테스트 케이스
     * C1. 테이블 반납 성공
     * C2. 테이블 반납 실패
         * C2-01. 테이블 반납 실패 - 사진 업로드 실패
     */

    @Test
    @DisplayName("C1. 테이블 반납 성공")
    void 테이블반납_성공() {
        // Given
        Long reqReservationId = 1L;
        Long memberId = 3L;
        Reservation findReservation = Reservation.builder()
                .member(Member.builder().memberId(3L).build())
                .reservationId(1L)
                .reservationStatus(ReservationStatus.IN_USE).build();
        given(reservationSearchableRepository.findByReservationId(reqReservationId)).willReturn(Optional.of(findReservation));
        given(tableReturnSearchableRepository.existsByReservationId(reqReservationId)).willReturn(false);

        MockMultipartFile cleanupPhoto = new MockMultipartFile("cleanupPhoto", "imageBytes".getBytes(StandardCharsets.UTF_8));
        given(fileUploadUtil.uploadReturnsImage(cleanupPhoto)).willReturn(Image.builder().build());

        given(tableReturnRepository.save(any())).willReturn(TableReturn.builder().build());

        // When
        tableReturnService.returnTable(memberId, reqReservationId, cleanupPhoto);

        // Then
        assertEquals(ReservationStatus.RETURNED, findReservation.getReservationStatus());
    }

    @Test
    @DisplayName("C2-01. 테이블 반납 실패 - 사진 업로드 실패")
    void 테이블반납_실패_사진업로드실패() {
        // Given
        Long reqReservationId = 1L;
        Long memberId = 3L;
        Reservation findReservation = Reservation.builder()
                .member(Member.builder().memberId(3L).build())
                .reservationId(1L)
                .reservationStatus(ReservationStatus.IN_USE).build();
        given(reservationSearchableRepository.findByReservationId(reqReservationId)).willReturn(Optional.of(findReservation));

        MockMultipartFile cleanupPhoto = new MockMultipartFile("cleanupPhoto", "imageBytes".getBytes(StandardCharsets.UTF_8));
        given(fileUploadUtil.uploadReturnsImage(cleanupPhoto)).willThrow(FileSystemException.class);

        // When, Then
        assertThrows(FileSystemException.class, () -> tableReturnService.returnTable(memberId, reqReservationId, cleanupPhoto));
    }
}