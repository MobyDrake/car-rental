package com.mobydrake.rental.service;


import com.mobydrake.rental.billing.InvoiceConfirmation;
import com.mobydrake.rental.entity.Rental;
import com.mobydrake.rental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceConfirmationService {

    private final RentalRepository rentalRepository;

    @KafkaListener(groupId = "invoice", topics = "invoices-confirmation")
    public void invoicePaid(InvoiceConfirmation invoiceConfirmation) {
        log.info("Received invoice confirmation {}", invoiceConfirmation);

        if (!invoiceConfirmation.paid()) {
            log.warn("Received unpaid invoice confirmation - {}", invoiceConfirmation);
        }
        InvoiceConfirmation.InvoiceReservation reservation = invoiceConfirmation.invoice().reservation();
        rentalRepository.findByUserIdAndReservationId(reservation.userId(), reservation.id())
                .ifPresentOrElse(rental -> {
                    // mark the already started rental as paid
                    rental.setPaid(true);
                    rentalRepository.save(rental);
                }, () -> {
                    // create new rental starting in the future
                    Rental rental = new Rental();
                    rental.setUserId(reservation.userId());
                    rental.setReservationId(reservation.id());
                    rental.setStartDate(reservation.startDate());
                    rental.setActive(false);
                    rental.setPaid(true);
                    rentalRepository.save(rental);
                });
    }
}
