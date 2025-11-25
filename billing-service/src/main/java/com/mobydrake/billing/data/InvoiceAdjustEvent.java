package com.mobydrake.billing.data;

import java.time.LocalDate;

public record InvoiceAdjustEvent(String rentalId, String userId, LocalDate actualEndDate, double price) {
}
