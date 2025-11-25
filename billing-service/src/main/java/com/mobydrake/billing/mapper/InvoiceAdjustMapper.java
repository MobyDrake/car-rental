package com.mobydrake.billing.mapper;

import com.mobydrake.billing.data.InvoiceAdjustEvent;
import com.mobydrake.billing.model.InvoiceAdjust;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface InvoiceAdjustMapper {

    InvoiceAdjust toEntity(InvoiceAdjustEvent event);
}
