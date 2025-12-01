package com.mobydrake.reservation.repository;

import com.mobydrake.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends ListCrudRepository<Reservation, Long> {

    @Query("from Reservation r where r.startDate > :endDay or r.endDate < :startDay")
    List<Reservation> findAllBetweenDates(LocalDate startDay, LocalDate endDay);

}
