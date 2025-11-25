package com.mobydrake.rental.service;

import com.mobydrake.rental.billing.InvoiceAdjustEvent;
import com.mobydrake.rental.reservation.Reservation;
import com.mobydrake.rental.reservation.ReservationClient;
import com.mobydrake.rental.entity.Rental;
import com.mobydrake.rental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentService {

    private static final Logger log = LoggerFactory.getLogger(RentService.class);
    private static final double STANDARD_REFUND_RATE_PER_DAY = -10.99;
    private static final double STANDARD_PRICE_FOR_PROLONGED_DAY = 25.99;

    private final RentalRepository rentalRepository;
    private final ReservationClient reservationClient;
    private final NewTopic invoicesAdjustTopic;
    private final KafkaTemplate<String, InvoiceAdjustEvent> kafkaTemplate;

    @Transactional
    public Rental startRent(String userId, Long reservationId) {
        Rental rental = rentalRepository.findByUserIdAndReservationId(userId, reservationId)
                .orElseGet(() -> Rental.builder()
                                       .userId(userId)
                                       .reservationId(reservationId)
                                       .startDate(LocalDate.now())
                                       .build());
        rental.setActive(true);
        rentalRepository.save(rental);
        return rental;
    }

    public Rental endRent(final String userId, final Long reservationId) {
        Rental rental = rentalRepository.findByUserIdAndReservationId(userId, reservationId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        //todo: implement
//        if (!rental.isPaid()) {
//            throw new IllegalCallerException("Rental not paid");
//        }

        final LocalDate actualEndDate = LocalDate.now();
        Reservation reservation = reservationClient.getById(reservationId);
        if (!reservation.endDay().isEqual(actualEndDate)) {
            log.info("Adjusting price for rental {}. Original reservation end day was {}.", rental, reservation.endDay());
            double price = computePrice(reservation.endDay(), actualEndDate);
            InvoiceAdjustEvent event = new InvoiceAdjustEvent(rental.getId(), rental.getUserId(), actualEndDate, price);
            kafkaTemplate.send(invoicesAdjustTopic.name(), event);
        }
        rental.setEndDate(actualEndDate);
        rental.setActive(false);
        rentalRepository.save(rental);
        return rental;
    }

    public List<Rental> allRent() {
        return rentalRepository.findAll();
    }

    public List<Rental> allActiveRent() {
        return rentalRepository.listActive();
    }

    private double computePrice(LocalDate endDate, LocalDate today) {
        return endDate.isBefore(today) ?
                ChronoUnit.DAYS.between(endDate, today) * STANDARD_PRICE_FOR_PROLONGED_DAY :
                ChronoUnit.DAYS.between(today, endDate) * STANDARD_REFUND_RATE_PER_DAY;
    }
}
