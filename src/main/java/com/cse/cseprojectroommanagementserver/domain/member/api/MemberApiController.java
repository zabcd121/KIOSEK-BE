package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.MemberComplexInfoSearchService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.MEMBER_MY_PAGE_INFO_SEARCH_SUCCESS;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberComplexInfoSearchService memberComplexInfoSearchService;

    @GetMapping("/v1/members/{id}")
    public ResponseSuccess<MemberComplexInfoRes> getMemberComplexInfo(@PathVariable("id") Long memberId) {
        MemberComplexInfoRes memberComplexInfo = memberComplexInfoSearchService.searchMemberComplexInfo(memberId);
        return new ResponseSuccess<>(MEMBER_MY_PAGE_INFO_SEARCH_SUCCESS, memberComplexInfo);
    }

}