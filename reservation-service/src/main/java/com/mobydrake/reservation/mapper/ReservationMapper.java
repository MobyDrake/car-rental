package com.mobydrake.reservation.mapper;

import com.mobydrake.reservation.dto.ReservationCreateDto;
import com.mobydrake.reservation.dto.ReservationDto;
import com.mobydrake.reservation.entity.Reservation;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDto toDto(Reservation source);

    Reservation toSource(ReservationDto dto);

    Reservation toSource(ReservationCreateDto dto);
}
