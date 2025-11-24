package com.mobydrake.rental.repository;

import com.mobydrake.rental.entity.Rental;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends ListCrudRepository<Rental, Long> {

    @Query("select r Rental r where userId = :userId and reservationId = :reservationId")
    Optional<Rental> findByUserIdAndReservationId(String userId, Long reservationId);

    @Query("select r Rental r where active = true")
    List<Rental> listActive();
}
