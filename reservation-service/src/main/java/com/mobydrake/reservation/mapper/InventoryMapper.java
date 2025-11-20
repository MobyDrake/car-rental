package com.mobydrake.reservation.mapper;

import com.mobydrake.reservation.data.InvoiceEvent;
import com.mobydrake.reservation.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {

    InvoiceEvent toEvent(Reservation reservation, double totalPrice);
}
