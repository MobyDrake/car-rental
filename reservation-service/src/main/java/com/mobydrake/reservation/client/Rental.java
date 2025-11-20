package com.mobydrake.reservation.client;

import java.time.LocalDate;

public record Rental(String id,
                     String userId,
                     Long reservationId,
                     LocalDate startDate) {}