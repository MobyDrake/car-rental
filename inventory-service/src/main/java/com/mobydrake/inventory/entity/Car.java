package com.mobydrake.inventory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDate createAt;

    @UpdateTimestamp
    private LocalDate updateAt;

    @Column
    private String licensePlateNumber;

    @Column
    private String manufacturer;

    @Column
    private String model;

}
