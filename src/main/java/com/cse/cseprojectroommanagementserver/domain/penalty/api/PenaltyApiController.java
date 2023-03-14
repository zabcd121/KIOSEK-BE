package com.cse.cseprojectroommanagementserver.domain.penalty.api;

import com.cse.cseprojectroommanagementserver.domain.penalty.application.PenaltySearchService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltyResDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.*;
import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PenaltyApiController {

    private final PenaltySearchService penaltySearchService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/v2/penalties")
    public ResponseSuccess<List<PenaltyLogRes>> getPenaltyListByMemberId(HttpServletRequest request) {
        Long memberId = Long.parseLong(jwtTokenProvider.getSubject(jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER))));
        List<PenaltyLogRes> penaltyLogResponseList = penaltySearchService.searchMemberPenaltyLogList(memberId);
        return new ResponseSuccess(PENALTY_LOGS_SEARCH_SUCCESS, penaltyLogResponseList);
    }
}
