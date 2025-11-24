package com.mobydrake.rental.billing;

import java.time.LocalDate;

public record InvoiceConfirmation(Invoice invoice, boolean paid) {

    public record Invoice(double totalPrice, boolean paid, InvoiceReservation reservation) {}

    public record InvoiceReservation(Long id, String userId, Long carId, LocalDate startDate, LocalDate endDate) {}
}
