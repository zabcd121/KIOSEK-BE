package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;

public interface ReservationPolicySearchableRepository {

    ReservationPolicy findCurrentlyPolicy();

}
