package com.cse.cseprojectroommanagementserver.domain.member.domain.model;

import com.cse.cseprojectroommanagementserver.global.common.Image;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountQR {

    @Id @GeneratedValue
    private Long accountQRId;

    @Embedded
    private Image qrCodeImg;

    @Setter
    @OneToOne(mappedBy = "accountQR", fetch = FetchType.LAZY)
    private Member member;


}
