package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TableDeactivationInfo {

    private String reason;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

}