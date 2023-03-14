package com.cse.cseprojectroommanagementserver.domain.tablereturn.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.NotExistsReservationException;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.exception.UnableToReturnAnotherUserReservationException;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.TableReturn;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository.TableReturnRepository;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import com.cse.cseprojectroommanagementserver.global.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TableReturnService {

    private final TableReturnRepository tableReturnRepository;
    private final ReservationSearchableRepository reservationSearchableRepository;
    private final FileUploadUtil fileUploadUtil;

    @Transactional
    public void returnTable(Long memberId, Long reservationId, MultipartFile cleanupPhoto) {
        Reservation findReservation = reservationSearchableRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new NotExistsReservationException());

        if(!findReservation.getMember().getMemberId().equals(memberId)) {
            System.out.println("자기 예약이 아닌것은 반납할 수 없음");
            throw new UnableToReturnAnotherUserReservationException();
        }

        System.out.println("upload 전");
        Image image = fileUploadUtil.uploadReturnsImage(cleanupPhoto);
        System.out.println("upload 후");

        tableReturnRepository.save(TableReturn.createReturn(findReservation, image));
    }
}