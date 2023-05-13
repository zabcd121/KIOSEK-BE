package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NumberOfSuspensionDay {

    @Column(nullable = false)
    private Integer numberOfSuspensionDay;

}
