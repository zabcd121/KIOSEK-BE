package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.MemberSearchService;
import com.cse.cseprojectroommanagementserver.global.dto.SuccessResponse;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.global.dto.SuccessCode.*;
import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberSearchService memberSearchService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/v2/members")
    public SuccessResponse<MemberComplexInfoRes> getMemberProfile(HttpServletRequest request) {
        String resolvedToken = jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER));

        MemberComplexInfoRes memberComplexInfo = memberSearchService.searchMyPageInfo(
                Long.parseLong(jwtTokenProvider.getSubject(resolvedToken)));
        return new SuccessResponse<>(MYPAGE_SEARCH_SUCCESS, memberComplexInfo);
    }

}