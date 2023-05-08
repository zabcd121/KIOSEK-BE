package com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository;

public interface TableReturnSearchableRepository {

    boolean existsByReservationId(Long reservationId);
}
