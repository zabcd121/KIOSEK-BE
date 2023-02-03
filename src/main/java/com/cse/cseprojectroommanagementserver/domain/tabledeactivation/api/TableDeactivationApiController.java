package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.api;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.application.TableDeactivateService;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationRequestDto;
import com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationRequestDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestController
@RequestMapping("/api/table-deactivations")
@RequiredArgsConstructor
public class TableDeactivationApiController {

    private final TableDeactivateService tableDeactivateService;

    @PostMapping
    public ResponseSuccessNoResult deactivateTables(@RequestBody TableDeactivationRequest tableDeactivationRequest) {
        tableDeactivateService.deactivateTables(tableDeactivationRequest);
        return new ResponseSuccessNoResult(TABLE_DEACTIVATION_SUCCESS);
    }
}
