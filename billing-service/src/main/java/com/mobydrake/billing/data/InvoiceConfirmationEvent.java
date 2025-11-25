package com.mobydrake.billing.data;

import com.mobydrake.billing.model.Invoice;

public record InvoiceConfirmationEvent(Invoice invoice, boolean paid) {}
