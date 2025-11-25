package com.mobydrake.billing.repository;

import com.mobydrake.billing.model.Invoice;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends ListCrudRepository<Invoice, Long> {
}
