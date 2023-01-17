package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NumberOfSuspensionDay {

    private Integer numberOfSuspensionDay;

}
