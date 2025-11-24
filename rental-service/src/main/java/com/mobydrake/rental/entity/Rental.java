package com.mobydrake.rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rental")
public class Rental {

    @Id
    private String id;
    private String userId;
    private Long reservationId;
    private LocalDate startDate;
    private LocalDate endDate;

    @BsonProperty("rental-active")
    private boolean active;
    private boolean paid;

}
