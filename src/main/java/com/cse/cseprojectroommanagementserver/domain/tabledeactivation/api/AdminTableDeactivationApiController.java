package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.api;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.application.TableDeactivateService;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.application.TableDeactivationLogSearchService;
import com.cse.cseprojectroommanagementserver.global.dto.SuccessResponse;
import com.cse.cseprojectroommanagementserver.global.dto.SuccessResponseNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationResDto.*;
import static com.cse.cseprojectroommanagementserver.global.dto.SuccessCode.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminTableDeactivationApiController {

    private final TableDeactivateService tableDeactivateService;
    private final TableDeactivationLogSearchService tableDeactivationLogSearchService;

    @PostMapping("/v1/table-deactivations")
    public SuccessResponseNoResult deactivateTables(@RequestBody @Validated TableDeactivationReq tableDeactivationRequest) {
        tableDeactivateService.deactivateTables(tableDeactivationRequest);
        return new SuccessResponseNoResult(TABLE_DEACTIVATE_SUCCESS);
    }

    @GetMapping("/v1/table-deactivations")
    public SuccessResponse<Page<TableDeactivationSearchRes>> getDeactivationTableList(Pageable pageable) {
        return new SuccessResponse(TABLE_DEACTIVATION_SEARCH_SUCCESS, tableDeactivationLogSearchService.searchTableDeactivationLog(pageable));
    }

}
