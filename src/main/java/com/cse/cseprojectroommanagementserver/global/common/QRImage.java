package com.cse.cseprojectroommanagementserver.global.common;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Embeddable
public class QRImage {
    @Column(nullable = false)
    private String fileLocalName;

    @Column(nullable = false)
    private String fileOriName;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private String content;
}
