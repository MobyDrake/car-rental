package com.mobydrake.reservation.repository;

import com.mobydrake.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReservationRepository extends ListCrudRepository<Reservation, Long> {

    @Query("select id from Reservation")
    Set<Long> findIds();
}
