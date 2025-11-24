package com.mobydrake.inventory.mapper;

import com.mobydrake.inventory.dto.CarCreate;
import com.mobydrake.inventory.entity.Car;
import inventory.model.CarResponse;
import inventory.model.InsertCarRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

    Car toSource(CarCreate carCreate);

    CarResponse toResponse(Car car);

    Car toSource(InsertCarRequest insertCarRequest);
}
