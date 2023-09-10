package com.cse.cseprojectroommanagementserver.domain.complaint.application;

import com.cse.cseprojectroommanagementserver.domain.complaint.domain.model.Complaint;
import com.cse.cseprojectroommanagementserver.domain.complaint.domain.repository.ComplaintRepository;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.repository.ProjectRoomRepository;
import com.cse.cseprojectroommanagementserver.global.dto.Image;
import com.cse.cseprojectroommanagementserver.global.util.fileupload.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintReqDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ComplainService {

    private final ComplaintRepository complaintRepository;
    private final ProjectRoomRepository projectRoomRepository;
    private final FileUploadUtil fileUploadUtil;

    public void complain(ComplainReq complainReq) {
        Image image = fileUploadUtil.uploadComplainsImage(complainReq.getPhoto());

        Complaint complaint = Complaint.createComplaint(
                projectRoomRepository.getReferenceById(complainReq.getProjectRoomId()), complainReq.getSubject(), complainReq.getContent(), image);

        System.out.println(complaint.getProjectRoom().getProjectRoomId());
        complaintRepository.save(complaint);
    }
}
