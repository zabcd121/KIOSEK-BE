package com.cse.cseprojectroommanagementserver.domain.visitor.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.visitor.domain.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    boolean existsByUserIpAndDate(String userIp, LocalDate date);

    @Query("select count(v) from Visitor v where v.date between ?1 and ?2")
    Integer countByDateBetween(LocalDate startDate, LocalDate endDate);
}
