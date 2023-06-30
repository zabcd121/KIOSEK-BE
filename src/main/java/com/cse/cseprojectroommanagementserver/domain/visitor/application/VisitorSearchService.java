package com.cse.cseprojectroommanagementserver.domain.visitor.application;

import com.cse.cseprojectroommanagementserver.domain.visitor.domain.repository.VisitorRepository;
import com.cse.cseprojectroommanagementserver.domain.visitor.dto.VisitorReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.domain.visitor.dto.VisitorReqDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VisitorSearchService {
    private final VisitorRepository visitorRepository;

    public Integer searchVisitorCount(VisitorCountDuringPeriodReq visitorCountDuringPeriodReq) {
        return visitorRepository.countByDateBetween(visitorCountDuringPeriodReq.getStartDate(), visitorCountDuringPeriodReq.getEndDate());
    }

}
