package com.cse.cseprojectroommanagementserver.domain.member.domain.model;

import com.cse.cseprojectroommanagementserver.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Embedded
    private Account account;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    private String stdNum;
    private String name;
    private String email;
}
