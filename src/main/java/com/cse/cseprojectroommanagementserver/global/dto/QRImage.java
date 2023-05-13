package com.cse.cseprojectroommanagementserver.global.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class QRImage {
    @Column(nullable = false, length = 50)
    private String fileLocalName;

    @Column(nullable = false, length = 50)
    private String fileOriName;

    @Column(nullable = false, length = 50)
    private String fileUrl;

    @Column(nullable = false, length = 50)
    private String content;


}
