package com.mobydrake.billing.data;


import com.mobydrake.billing.model.Invoice;
import lombok.Data;

@Data
public class ReservationInvoice {

    private Invoice.Reservation reservation;
    private double price;
}
