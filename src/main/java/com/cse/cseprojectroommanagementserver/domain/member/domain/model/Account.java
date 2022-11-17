package com.cse.cseprojectroommanagementserver.domain.member.domain.model;

import lombok.*;

import javax.persistence.*;
import java.io.File;

@Embeddable
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    private String loginId;
    private String password;

}
