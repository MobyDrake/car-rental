package com.mobydrake.reservation.dto;

public record CarDto(Long id,
                     String licensePlateNumber,
                     String manufacturer,
                     String model) {}
