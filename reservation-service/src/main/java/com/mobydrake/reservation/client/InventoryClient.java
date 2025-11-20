package com.mobydrake.reservation.client;

import com.mobydrake.reservation.dto.CarDto;

import java.util.List;
import java.util.Optional;

public interface InventoryClient {

    List<CarDto> findAllCars();

    Optional<CarDto> findCarById(Long id);
}
