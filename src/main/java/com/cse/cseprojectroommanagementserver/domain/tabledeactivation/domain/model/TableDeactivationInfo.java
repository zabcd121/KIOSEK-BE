package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TableDeactivationInfo {

    private String reason;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}