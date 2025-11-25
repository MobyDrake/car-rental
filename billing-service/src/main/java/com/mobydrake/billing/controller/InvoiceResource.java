package com.mobydrake.billing.controller;

import com.mobydrake.billing.model.Invoice;
import com.mobydrake.billing.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceResource {

    private final InvoiceRepository invoiceRepository;

    @GetMapping
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }
}
