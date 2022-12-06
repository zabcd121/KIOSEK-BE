package com.cse.cseprojectroommanagementserver.domain.tablereturn.api;

import com.cse.cseprojectroommanagementserver.domain.tablereturn.application.TableReturnService;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.dto.TableReturnRequestDto;
import com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.domain.tablereturn.dto.TableReturnRequestDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestController
@RequestMapping("/api/returns/")
@RequiredArgsConstructor
public class TableReturnApiController {

    private final TableReturnService tableReturnService;

    @PostMapping
    public ResponseSuccessNoResult tableReturn(@RequestBody TableReturnRequest tableReturnRequest) {
        tableReturnService.returnTable(tableReturnRequest);
        return new ResponseSuccessNoResult(RETURN_SUCCESS);
    }
}
