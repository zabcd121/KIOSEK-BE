package com.cse.cseprojectroommanagementserver.domain.member.dto;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberResponseDto {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class LoginResponse {

        private TokensDto tokenInfo;
        private LoginMemberInfoResponse memberInfo;


    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class LoginMemberInfoResponse {
        private Long memberId;
        private String name;
        private RoleType roleType;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class TokensDto {
        private String accessToken;
        private String refreshToken;

    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class MemberComplexInfoResponse {
        private QRImageResponse qrImage;
        private Long violationCount;
        private MemberPenaltyInfo penaltyInfo;
        private Long pastReservationCount;

        public MemberComplexInfoResponse of(AccountQR accountQR, Long violationsCount, Penalty penalty, Long pastReservationsCount) {
            this.qrImage = new QRImageResponse().of(accountQR);
            this.violationCount = violationsCount;
            if(penalty==null) {
                penaltyInfo = null;
            } else {
                this.penaltyInfo = new MemberPenaltyInfo().of(penalty);
            }
            this.pastReservationCount = pastReservationsCount;

            return this;
        }

    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class QRImageResponse {
        private String imageName;
        private String imageURL;

        public QRImageResponse of(AccountQR accountQR) {
            this.imageName = accountQR.getQrImage().getFileLocalName();
            this.imageURL = accountQR.getQrImage().getFileUrl();
            return this;
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class MemberPenaltyInfo{
        private LocalDate startDateTime;
        private LocalDate endDateTime;

        public MemberPenaltyInfo of(Penalty penalty) {
            this.startDateTime = penalty.getStartDate();
            this.endDateTime = penalty.getEndDate();

            return this;
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class MemberSimpleInfo {
        private Long memberId;
        private String loginId;
        private String name;
    }

}
