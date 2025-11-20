package com.mobydrake.reservation.data;

public record InvoiceEvent(double totalPrice,
                           ReservationData reservation) {}
