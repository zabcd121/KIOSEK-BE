package com.cse.cseprojectroommanagementserver.global.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

//@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Embeddable
public class Image{

    private String fileLocalName;
    private String fileOriName;
    private String fileUrl;
}
