package com.mobydrake.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long carId;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private String userId;

    @CreationTimestamp
    private LocalDate createAt;

    @UpdateTimestamp
    private LocalDate updateAt;
}
