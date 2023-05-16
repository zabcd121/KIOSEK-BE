package com.cse.cseprojectroommanagementserver.unittest.domain.complaint.application;

import com.cse.cseprojectroommanagementserver.domain.complaint.application.ComplainService;
import com.cse.cseprojectroommanagementserver.domain.complaint.domain.model.Complaint;
import com.cse.cseprojectroommanagementserver.domain.complaint.domain.repository.ComplaintRepository;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.repository.ProjectRoomRepository;
import com.cse.cseprojectroommanagementserver.global.dto.Image;
import com.cse.cseprojectroommanagementserver.global.error.exception.FileSystemException;
import com.cse.cseprojectroommanagementserver.global.util.fileupload.FileUploadUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintReqDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ComplainServiceUnitTest {

    @InjectMocks
    ComplainService complainService;

    @Mock ComplaintRepository complaintRepository;
    @Mock ProjectRoomRepository projectRoomRepository;
    @Mock FileUploadUtil fileUploadUtil;

    /** 테스트 케이스
     * C1. 민원 접수 성공
     * C2. 민원 접수 실패
         * C2-01. 민원 접수 실패 - 이미지 업로드 실패
     */

    @Test
    @DisplayName("C1. 민원 접수 성공")
    void 민원접수_성공() {
        // Given

        MockMultipartFile photo = new MockMultipartFile("example", "example".getBytes(StandardCharsets.UTF_8));
        ComplainReq complainReq = ComplainReq.builder().projectRoomId(1L).subject("subject").content("content").photo(photo).build();
        Image uploadedImage = Image.builder().fileLocalName("localName").fileOriName("oriName").fileUrl("/image").build();
        given(fileUploadUtil.uploadComplainsImage(photo)).willReturn(uploadedImage);

        ProjectRoom projectRoom = ProjectRoom.builder().projectRoomId(1L).build();
        given(projectRoomRepository.getReferenceById(complainReq.getProjectRoomId())).willReturn(projectRoom);

        Complaint savedComplaint = Complaint.builder().complaintId(1L).projectRoom(projectRoom).subject("subject").content("content").image(uploadedImage).build();
        given(complaintRepository.save(any())).willReturn(savedComplaint);

        // When
        complainService.complain(complainReq);

        // Then
        assertDoesNotThrow(() -> FileSystemException.class);
        then(complaintRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("C2-01. 민원 접수 실패 - 이미지 업로드 실패")
    void 민원접수_실패_이미지업로드실패() {
        // Given
        MockMultipartFile photo = new MockMultipartFile("example", "example".getBytes(StandardCharsets.UTF_8));
        ComplainReq complainReq = ComplainReq.builder().projectRoomId(1L).subject("subject").content("content").photo(photo).build();
        given(fileUploadUtil.uploadComplainsImage(photo)).willThrow(FileSystemException.class);

        // When, Then
        assertThrows(FileSystemException.class, () -> complainService.complain(complainReq));
    }
}