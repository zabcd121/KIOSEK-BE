package com.cse.cseprojectroommanagementserver.domain.member.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.File;

@Embeddable
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    // 암호화 해서 저장하기 때문에 길이를 길게 해놓음
    @Column(unique = true, nullable = false, length = 100)
    private String loginId;

    // 암호화 해서 저장하기 때문에 길이를 길게 해놓음
    @Column(nullable = false, length = 100)
    private String password;

}
