package com.mobydrake.billing.repository;

import com.mobydrake.billing.model.InvoiceAdjust;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceAdjustRepository extends CrudRepository<InvoiceAdjust, Long> {
}
