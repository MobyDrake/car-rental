package com.mobydrake.reservation.client;

import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RentalClient {

    private static final String BASE_URL = "http://localhost:8082";

    private final RestClient restClient;

    public RentalClient() {
        this.restClient = RestClient.builder().baseUrl(BASE_URL).build();
    }

    public Rental start(@NonNull String userId,
                        @NonNull Long reservationId) {
        ResponseEntity<Rental> responseEntity = restClient.post()
                .uri("/rental/start/{userId}/{reservationId}", userId, reservationId)
                .retrieve()
                .toEntity(Rental.class);
        return responseEntity.getBody();
    }
}
