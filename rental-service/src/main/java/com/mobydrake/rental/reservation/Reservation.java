package com.mobydrake.rental.reservation;

import java.time.LocalDate;

public record Reservation(Long id,
                          Long carId,
                          LocalDate startDay,
                          LocalDate endDay,
                          String userId) {}
