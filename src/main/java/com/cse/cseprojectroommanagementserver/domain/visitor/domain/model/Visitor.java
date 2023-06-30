package com.cse.cseprojectroommanagementserver.domain.visitor.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Visitor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitorId;

    @Column(nullable = false)
    private String userIp;

    @Column(nullable = false)
    private String userAgent;

    @Column(updatable = false,  nullable = false)
    private LocalDate date;
}
