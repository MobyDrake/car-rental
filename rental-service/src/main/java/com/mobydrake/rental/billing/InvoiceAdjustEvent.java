package com.mobydrake.rental.billing;

import java.time.LocalDate;

public record InvoiceAdjustEvent(String rentalId, String userId, LocalDate actualEndDate, double price) {}
