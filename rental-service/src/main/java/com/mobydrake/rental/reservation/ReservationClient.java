package com.mobydrake.rental.reservation;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ReservationClient {

    private static final String BASE_URL = "http://localhost:8081";

    private final RestClient restClient;

    public ReservationClient() {
        this.restClient = RestClient.builder().baseUrl(BASE_URL).build();
    }

    public Reservation getById(@NonNull Long id) {
        return restClient.get()
                .uri("/reservation/{id}", id)
                .retrieve()
                .toEntity(Reservation.class)
                .getBody();
    }
}
