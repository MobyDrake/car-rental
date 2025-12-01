package com.mobydrake.reservation.controller;

import com.mobydrake.reservation.dto.CarDto;
import com.mobydrake.reservation.dto.ReservationCreateDto;
import com.mobydrake.reservation.dto.ReservationDto;
import com.mobydrake.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    public ResponseEntity<@NonNull List<CarDto>> availability(@RequestParam LocalDate start,
                                                              @RequestParam LocalDate end) {
        return ResponseEntity.ok(reservationService.availability(start, end));
    }
}
