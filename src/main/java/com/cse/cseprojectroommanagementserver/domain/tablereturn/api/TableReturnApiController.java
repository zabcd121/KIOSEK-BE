package com.cse.cseprojectroommanagementserver.domain.tablereturn.api;

import com.cse.cseprojectroommanagementserver.domain.tablereturn.application.TableReturnService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TableReturnApiController {

    private final TableReturnService tableReturnService;

    @PostMapping("/v1/returns")
    public ResponseSuccessNoResult tableReturn(@RequestParam Long reservationId, MultipartFile cleanupPhoto) {
        tableReturnService.returnTable(reservationId, cleanupPhoto);
        return new ResponseSuccessNoResult(RETURN_SUCCESS);
    }
}