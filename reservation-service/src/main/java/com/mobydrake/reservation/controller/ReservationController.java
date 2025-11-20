package com.mobydrake.reservation.controller;

import com.mobydrake.reservation.dto.CarDto;
import com.mobydrake.reservation.dto.ReservationCreateDto;
import com.mobydrake.reservation.dto.ReservationDto;
import com.mobydrake.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<@NonNull List<ReservationDto>> getAllReservation() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull ReservationDto> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<@NonNull ReservationDto> makeReservation(@RequestBody ReservationCreateDto reservationDto) {
        return ResponseEntity.ok(reservationService.save(reservationDto));
    }

    @GetMapping("/availability")
    public ResponseEntity<@NonNull Collection<CarDto>> availability() {
        return ResponseEntity.ok(reservationService.availability());
    }
}
