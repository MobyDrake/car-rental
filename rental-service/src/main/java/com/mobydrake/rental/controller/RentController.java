package com.mobydrake.rental.controller;

import com.mobydrake.rental.entity.Rental;
import com.mobydrake.rental.service.RentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rental")
@RequiredArgsConstructor
public class RentController {

    private static final Logger log = LoggerFactory.getLogger(RentController.class);

    private final RentService rentService;

    @PostMapping("/start/{userId}/{reservationId}")
    public Rental start(String userId, Long reservationId) {
        log.info("Starting rental for {}, with reservation {}", userId, reservationId);
        return rentService.startRent(userId, reservationId);
    }

    @PutMapping("/end/{userId}/{reservationId}")
    public Rental end(String userId, Long reservationId) {
        log.info("Ending rental for {} with reservation {}", userId, reservationId);
        return rentService.endRent(userId, reservationId);
    }

    @GetMapping
    public List<Rental> list() {
        return rentService.allRent();
    }

    @GetMapping("/active")
    public List<Rental> listActive() {
        return rentService.allActiveRent();
    }
}
