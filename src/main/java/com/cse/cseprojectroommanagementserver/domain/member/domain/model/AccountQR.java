package com.cse.cseprojectroommanagementserver.domain.member.domain.model;

import com.cse.cseprojectroommanagementserver.global.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountQR {

    @Id @GeneratedValue
    private Long accountQRId;

    @Embedded
    private Image qrCodeImg;
}
