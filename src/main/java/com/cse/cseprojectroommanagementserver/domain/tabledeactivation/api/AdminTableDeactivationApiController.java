package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.api;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.application.TableDeactivateService;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.application.TableDeactivationLogSearchService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationRequestDto.*;
import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationResponseDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestController
@RequestMapping("/api/admins/table-deactivations")
@RequiredArgsConstructor
public class AdminTableDeactivationApiController {

    private final TableDeactivateService tableDeactivateService;
    private final TableDeactivationLogSearchService tableDeactivationLogSearchService;

    @PostMapping
    public ResponseSuccessNoResult deactivateTables(@RequestBody TableDeactivationRequest tableDeactivationRequest) {
        tableDeactivateService.deactivateTables(tableDeactivationRequest);
        return new ResponseSuccessNoResult(TABLE_DEACTIVATE_SUCCESS);
    }

    @GetMapping
    public ResponseSuccess<Page<SearchTableDeactivationListResponse>> getDeactivationTableList(Pageable pageable) {
        return new ResponseSuccess(TABLE_DEACTIVATION_SEARCH_SUCCESS, tableDeactivationLogSearchService.searchTableDeactivationLog(pageable));
    }
}
