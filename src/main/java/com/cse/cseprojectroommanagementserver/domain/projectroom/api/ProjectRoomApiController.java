package com.cse.cseprojectroommanagementserver.domain.projectroom.api;

import com.cse.cseprojectroommanagementserver.domain.projectroom.application.ProjectRoomSearchService;
import com.cse.cseprojectroommanagementserver.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.projectroom.dto.ProjectRoomResDto.*;
import static com.cse.cseprojectroommanagementserver.global.success.SuccessCode.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectRoomApiController {

    private final ProjectRoomSearchService projectRoomSearchService;

    @GetMapping("/v1/rooms")
    public SuccessResponse<List<ProjectRoomAndTableSearchRes>> getProjectRoomAndTableList() {
        return new SuccessResponse<>(PROJECT_ROOM_SEARCH_SUCCESS, projectRoomSearchService.searchAllProjectRoomAndTable());
    }
}
