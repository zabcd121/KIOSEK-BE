package com.cse.cseprojectroommanagementserver.domain.complaint.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.complaint.domain.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
