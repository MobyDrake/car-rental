package com.mobydrake.reservation.service;

import com.mobydrake.reservation.client.InventoryClient;
import com.mobydrake.reservation.client.Rental;
import com.mobydrake.reservation.client.RentalClient;
import com.mobydrake.reservation.data.InvoiceEvent;
import com.mobydrake.reservation.dto.CarDto;
import com.mobydrake.reservation.dto.ReservationCreateDto;
import com.mobydrake.reservation.dto.ReservationDto;
import com.mobydrake.reservation.entity.Reservation;
import com.mobydrake.reservation.mapper.InventoryMapper;
import com.mobydrake.reservation.mapper.ReservationMapper;
import com.mobydrake.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);
    private static final double STANDARD_RATE_PER_DAY = 19.99;

    private final RentalClient rentalClient;
    private final RabbitTemplate rabbitTemplate;
    private final InventoryClient inventoryClient;
    private final InventoryMapper inventoryMapper;
    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;
    private final Queue invoicesRequestQueue;

    public List<ReservationDto> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    public ReservationDto findById(Long id) {
        return reservationRepository.findById(id)
                                    .map(reservationMapper::toDto)
                                    .orElseThrow(RuntimeException::new);
    }

    public ReservationDto save(ReservationCreateDto reservationDto) {
        Reservation reservation = reservationMapper.toSource(reservationDto);
        reservationRepository.save(reservation);
        sendReservationInvoice(reservation);
        if (reservation.getStartDate().equals(LocalDate.now())) {
            Rental rental = rentalClient.start(reservation.getUserId(), reservation.getId());
            log.info("Successfully started rental {}", rental);
        }
        return reservationMapper.toDto(reservation);
    }

    public List<CarDto> availability(LocalDate startDay, LocalDate endDay) {
        List<Reservation> reservations = reservationRepository.findAllBetweenDates(startDay, endDay);
        Set<Long> reservationCarIds = reservations.stream().map(Reservation::getCarId).collect(Collectors.toSet());
        return inventoryClient.findAllCars()
                .stream()
                .filter(car -> !reservationCarIds.contains(car.id()))
                .toList();
    }

    private void sendReservationInvoice(Reservation reservation) {
        InvoiceEvent event = inventoryMapper.toEvent(reservation, computePrice(reservation));
        rabbitTemplate.convertAndSend(invoicesRequestQueue.getName(), "reservation-invoice", event);
    }

    private double computePrice(Reservation reservation) {
        return (ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate()) + 1)
                * STANDARD_RATE_PER_DAY;
    }
}
