package com.cse.cseprojectroommanagementserver.global.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

//@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Embeddable
public class Image{

    @Column(length = 50)
    private String fileLocalName;

    @Column(length = 50)
    private String fileOriName;

    @Column(length = 50)
    private String fileUrl;
}
