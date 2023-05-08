package com.cse.cseprojectroommanagementserver.domain.tablereturn.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.NotExistsReservationException;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository.TableReturnSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.exception.UnableToReturnException;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.TableReturn;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository.TableReturnRepository;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import com.cse.cseprojectroommanagementserver.global.util.FileUploadUtil;
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
                .orElseThrow(() -> new NotExistsReservationException());

        if(!isMyReservation(memberId, findReservation) || isAlreadyReturnedReservation(reservationId)) {
            throw new UnableToReturnException();
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