package com.cse.cseprojectroommanagementserver.domain.tablereturn.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository.TableReturnSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.TableReturn;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository.TableReturnRepository;
import com.cse.cseprojectroommanagementserver.global.dto.Image;
import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessRuleException;
import com.cse.cseprojectroommanagementserver.global.error.exception.NotFoundException;
import com.cse.cseprojectroommanagementserver.global.error.exception.UnAuthorizedException;
import com.cse.cseprojectroommanagementserver.global.util.fileupload.FileUploadUtil;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TableReturnService {

    private final TableReturnRepository tableReturnRepository;
    private final TableReturnSearchableRepository tableReturnSearchableRepository;
    private final ReservationSearchableRepository reservationSearchableRepository;
    private final FileUploadUtil fileUploadUtil;


    @Timed("kiosek.return")
    @Transactional
    public void returnTable(Long memberId, Long reservationId, MultipartFile cleanupPhoto) {
        Reservation findReservation = reservationSearchableRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_RESERVATION));

        if(!isMyReservation(memberId, findReservation)) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_RETURN);
        }

        if(isAlreadyReturnedReservation(reservationId)) {
            throw new BusinessRuleException(ErrorCode.RETURN_IMPOSSIBLE_STATUS);
        }

        Image image = fileUploadUtil.uploadReturnsImage(cleanupPhoto);

        tableReturnRepository.save(TableReturn.createReturn(findReservation, image));
    }

    private boolean isMyReservation(Long memberId, Reservation reservation) {
        return reservation.getMember().getMemberId().equals(memberId);
    }

    private boolean isAlreadyReturnedReservation(Long reservationId) {
        return tableReturnSearchableRepository.existsByReservationId(reservationId);
    }
}