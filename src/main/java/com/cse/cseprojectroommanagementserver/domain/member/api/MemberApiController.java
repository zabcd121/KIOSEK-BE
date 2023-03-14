package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.MemberSearchService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.MEMBER_MY_PAGE_INFO_SEARCH_SUCCESS;
import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberSearchService memberSearchService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/v2/members")
    public ResponseSuccess<MemberComplexInfoRes> getMemberProfile(HttpServletRequest request) {
        String resolvedToken = jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER));

        MemberComplexInfoRes memberComplexInfo = memberSearchService.searchMemberComplexInfo(
                Long.parseLong(jwtTokenProvider.getSubject(resolvedToken)));
        return new ResponseSuccess<>(MEMBER_MY_PAGE_INFO_SEARCH_SUCCESS, memberComplexInfo);
    }

}