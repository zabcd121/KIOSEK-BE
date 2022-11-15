package com.cse.cseprojectroommanagementserver.domain.member.domain.model;

import com.cse.cseprojectroommanagementserver.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Embedded
    private Account account;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    private String name;
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_QR")
    AccountQR accountQR;

    public String getLoginId() {
        return account.getLoginId();
    }
    public String getPassword() {
        return account.getPassword();
    }

    public List<String> getAuthorities() {
        List roleList = new ArrayList();

        if(!roleType.equals("")) {
            roleList.add(roleType.toString());
        }

        return roleList;
    }

}
