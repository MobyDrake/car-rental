package com.mobydrake.billing.data;

public record InvoiceEvent(double totalPrice, ReservationData reservation) {
}
