package com.cse.cseprojectroommanagementserver.domain.member.dto;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import lombok.*;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.TokenDto.*;

public class MemberResDto {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class LoginRes {
        private TokensDto tokenInfo;
        private LoginMemberInfoRes memberInfo;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class LoginMemberInfoRes {
        private Long memberId;
        private String name;
        private RoleType roleType;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class MemberComplexInfoRes {
        private QRImageRes qrImage;
        private Long violationCount;
        private MemberPenaltyInfoRes penaltyInfo;
        private Long pastReservationCount;

        public MemberComplexInfoRes of(AccountQR accountQR, Long violationsCount, Penalty penalty, Long pastReservationsCount) {
            this.qrImage = new QRImageRes().of(accountQR);
            this.violationCount = violationsCount;
            if(penalty==null) {
                penaltyInfo = null;
            } else {
                this.penaltyInfo = new MemberPenaltyInfoRes().of(penalty);
            }
            this.pastReservationCount = pastReservationsCount;

            return this;
        }

    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class QRImageRes {
        private String imageName;
        private String imageURL;

        public QRImageRes of(AccountQR accountQR) {
            this.imageName = accountQR.getQrImage().getFileLocalName();
            this.imageURL = accountQR.getQrImage().getFileUrl();
            return this;
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class MemberPenaltyInfoRes {
        private LocalDate startDateTime;
        private LocalDate endDateTime;

        public MemberPenaltyInfoRes of(Penalty penalty) {
            this.startDateTime = penalty.getStartDate();
            this.endDateTime = penalty.getEndDate();

            return this;
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class MemberSimpleInfoRes {
        private Long memberId;
        private String loginId;
        private String name;
    }

}
