package com.mobydrake.reservation.dto;

import java.time.LocalDate;

public record ReservationCreateDto(Long carId,
                                   LocalDate startDay,
                                   LocalDate endDay,
                                   String userId) {
}
