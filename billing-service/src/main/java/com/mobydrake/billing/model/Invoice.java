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
public class Invoice {

    @Id
    private String id;
    private double totalPrice;
    private boolean paid;
    private Reservation reservation;

    public record Reservation(Long id, String userId, Long carId, LocalDate startDate, LocalDate endDate) {}
}
