package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.api;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.application.TableDeactivateService;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.application.TableDeactivationLogSearchService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationResDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminTableDeactivationApiController {

    private final TableDeactivateService tableDeactivateService;
    private final TableDeactivationLogSearchService tableDeactivationLogSearchService;

    @PostMapping("/v1/table-deactivations")
    public ResponseSuccessNoResult deactivateTables(@RequestBody TableDeactivationReq tableDeactivationRequest) {
        tableDeactivateService.deactivateTables(tableDeactivationRequest);
        return new ResponseSuccessNoResult(TABLE_DEACTIVATE_SUCCESS);
    }

    @GetMapping("/v1/table-deactivations")
    public ResponseSuccess<Page<AdminTableDeactivationSearchRes>> getDeactivationTableList(Pageable pageable) {
        return new ResponseSuccess(TABLE_DEACTIVATION_SEARCH_SUCCESS, tableDeactivationLogSearchService.searchTableDeactivationLog(pageable));
    }

}
