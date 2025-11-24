package com.mobydrake.inventory.controller;

import com.mobydrake.inventory.dto.CarCreate;
import com.mobydrake.inventory.entity.Car;
import com.mobydrake.inventory.exception.EntityNotFound;
import com.mobydrake.inventory.mapper.CarMapper;
import com.mobydrake.inventory.repository.CarRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CarController {

    private static final String CAR_NOT_FOUND = "Car with ID [%d] not found";

    private final CarMapper carMapper;
    private final CarRepository carRepository;

    public CarController(CarMapper carMapper, CarRepository carRepository) {
        this.carMapper = carMapper;
        this.carRepository = carRepository;
    }

    @QueryMapping
    public List<Car> cars() {
        return carRepository.findAll();
    }

    @QueryMapping
    public Car carById(@Argument Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound(CAR_NOT_FOUND.formatted(id)));
    }

    @MutationMapping
    public Car registry(@Argument CarCreate car) {
        return carRepository.save(carMapper.toSource(car));
    }
}
