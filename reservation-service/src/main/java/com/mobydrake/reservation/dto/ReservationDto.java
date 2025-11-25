package com.mobydrake.reservation.dto;

import java.time.LocalDate;

public record ReservationDto(Long id,
                             Long carId,
                             LocalDate startDate,
                             LocalDate endDate,
                             String userId) {
}
