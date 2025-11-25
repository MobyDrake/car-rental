package com.mobydrake.billing.data;

import java.time.LocalDate;

public record ReservationData(Long id, String userId, Long carId, LocalDate startDate, LocalDate endDate) {}
