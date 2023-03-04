package com.cse.cseprojectroommanagementserver.domain.complaint.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.complaint.domain.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
