package com.cse.cseprojectroommanagementserver.domain.member.dto;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import lombok.*;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.TokenDto.*;

public class MemberResDto {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class LoginRes {
        private TokensDto tokenInfo;
        private LoginMemberInfoRes memberInfo;

        public static LoginRes of(TokensDto tokensDto, LoginMemberInfoRes memberInfoRes) {
            return new LoginRes(tokensDto, memberInfoRes);
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class LoginMemberInfoRes {
        private Long memberId;
        private String name;
        private RoleType roleType;

        public static LoginMemberInfoRes of(Member member) {
            return LoginMemberInfoRes.builder()
                    .memberId(member.getMemberId())
                    .name(member.getName())
                    .roleType(member.getRoleType())
                    .build();
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class MemberComplexInfoRes {
        private QRImageRes qrImage;
        private Long violationCount;
        private MemberPenaltyInfoRes penaltyInfo;
        private Long pastReservationCount;
        private int penaltyCount;

        public static MemberComplexInfoRes of(AccountQR accountQR, Long violationsCount, Penalty penalty, Long pastReservationsCount, int penaltyCount) {
            return MemberComplexInfoRes.builder()
                    .qrImage(QRImageRes.of(accountQR.getQrImage()))
                    .violationCount(violationsCount)
                    .penaltyInfo(penalty == null ? null : MemberPenaltyInfoRes.of(penalty))
                    .pastReservationCount(pastReservationsCount)
                    .penaltyCount(penaltyCount)
                    .build();
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class QRImageRes {
        private String imageName;
        private String imageURL;

        public static QRImageRes of(QRImage qrImage) {
            return new QRImageRes(qrImage.getFileLocalName(), qrImage.getFileUrl());
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class MemberPenaltyInfoRes {
        private LocalDate startDt;
        private LocalDate endDt;

        public static MemberPenaltyInfoRes of(Penalty penalty) {
            return new MemberPenaltyInfoRes(penalty.getStartDt(), penalty.getEndDt());
        }
    }

    @NoArgsConstructor
    @Getter @Setter //여기 @Setter는 비지니스 로직중에 필요해서 추가한것임
    public static class MemberSimpleInfoRes {
        private Long memberId;
        private String loginId;
        private String name;
    }

}
