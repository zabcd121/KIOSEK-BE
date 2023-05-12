package com.cse.cseprojectroommanagementserver.domain.tablereturn.api;

import com.cse.cseprojectroommanagementserver.domain.tablereturn.application.TableReturnService;
import com.cse.cseprojectroommanagementserver.global.dto.SuccessResponseNoResult;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.cse.cseprojectroommanagementserver.domain.tablereturn.dto.TableReturnReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.dto.SuccessCode.*;
import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TableReturnApiController {

    private final TableReturnService tableReturnService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/v1/returns", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE} )
    public SuccessResponseNoResult tableReturn(@ModelAttribute TableReturnReq tableReturnReq, HttpServletRequest request) {
        Long memberId = Long.parseLong(jwtTokenProvider.getSubject(jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER))));
        tableReturnService.returnTable(memberId, tableReturnReq.getReservationId(), tableReturnReq.getCleanupPhoto());
        return new SuccessResponseNoResult(RETURN_SUCCESS);
    }
}