package com.cse.cseprojectroommanagementserver.domain.usebanLog.domain.model;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UseBanLog implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USEBANINFO_ID")
    private UseBanInfo useBanInfo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DESK_ID")
    private ProjectTable bannedProjectTable;
}
