package com.cse.cseprojectroommanagementserver.domain.penalty.domain.model;

import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.Violation;
import com.cse.cseprojectroommanagementserver.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Penalty extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long penaltyId;

    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Violation> violations;

    private LocalDate startDate;
    private LocalDate endDate;

}
