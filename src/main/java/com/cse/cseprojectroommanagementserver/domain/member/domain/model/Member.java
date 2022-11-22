package com.cse.cseprojectroommanagementserver.domain.member.domain.model;

import com.cse.cseprojectroommanagementserver.global.common.BaseEntity;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import lombok.*;

import javax.persistence.*;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_QR")
    private AccountQR accountQR;

    @Embedded
    private Account account;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    private String name;
    private String email;

    public void changeAccountQR(AccountQR accountQR) {
        if(this.accountQR != null) {
            this.accountQR.setMember(null);
        }
        this.accountQR = accountQR;
        this.accountQR.setMember(this);
    }

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

    public static Member createMember(Account account, String email, String name, QRImage accountQRCodeImage) {
        Member member = Member.builder()
                .account(account)
                .email(email)
                .name(name)
                .roleType(RoleType.ROLE_MEMBER)
                .build();

        member.changeAccountQR(AccountQR.builder().qrCodeImg(accountQRCodeImage).build());
        return member;
    }

}
