package com.mobydrake.billing.mapper;

import com.mobydrake.billing.data.InvoiceConfirmationEvent;
import com.mobydrake.billing.data.InvoiceEvent;
import com.mobydrake.billing.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface InvoiceMapper {

    Invoice toEntity(InvoiceEvent event);

    @Mapping(target = "paid", source = "entity.paid")
    @Mapping(target = "invoice", source = "entity")
    InvoiceConfirmationEvent toConfirmationEvent(Invoice entity);
}
