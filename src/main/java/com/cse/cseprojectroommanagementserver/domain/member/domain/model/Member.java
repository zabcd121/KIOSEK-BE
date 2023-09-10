package com.cse.cseprojectroommanagementserver.domain.member.domain.model;

import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(indexes = @Index(name = "ix_name", columnList = "name"))
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Embedded
    private Account account;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_qr")
    private AccountQR accountQR;

    @Column(nullable = false, length = 20)
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    public static Member createMember(Account account, String email, String name, QRImage accountQRCodeImage) {
        Member member = Member.builder()
                .account(account)
                .email(email)
                .name(name)
                .roleType(RoleType.ROLE_MEMBER)
                .build();

        member.changeAccountQR(AccountQR.builder().qrImage(accountQRCodeImage).build());
        return member;
    }

    public void changeAccountQR(AccountQR accountQR) {
        if(this.accountQR != null) {
            this.accountQR.setMember(null);
        }
        this.accountQR = accountQR;
        this.accountQR.setMember(this);
    }

    public void changePassword(String newEncodedPassword) {
        this.account.changePassword(newEncodedPassword);
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
}
