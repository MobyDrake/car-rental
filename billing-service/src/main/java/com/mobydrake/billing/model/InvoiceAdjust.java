package com.mobydrake.billing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceAdjust {

    @Id
    private String id;
    private String rentalId;
    private String userId;
    private LocalDate actualEndDate;
    private double price;
    private boolean paid;
}
