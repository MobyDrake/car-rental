package com.mobydrake.inventory.repository;

import com.mobydrake.inventory.entity.Car;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends ListCrudRepository<Car, Long> {

    List<Car> findByModel(String model);

    Optional<Car> findByLicensePlateNumber(String licensePlateNumber);
}
