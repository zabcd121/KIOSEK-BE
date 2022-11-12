package com.cse.cseprojectroommanagementserver.domain.usebannedtable.domain.model;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UseBannedTable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USEBAN_ID")
    private UseBan useBan;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TABLE_ID")
    private Table bannedTable;
}
