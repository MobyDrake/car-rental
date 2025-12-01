package com.mobydrake.reservation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:8082", name = "rental")
public interface RentalClient {

    @PostMapping("/start/{userId}/{reservationId}")
    Rental start(@PathVariable String userId, @PathVariable Long reservationId);
}
