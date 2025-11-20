package com.mobydrake.reservation.client;

import com.mobydrake.reservation.dto.CarDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
public class InventoryGraphQLClient implements InventoryClient {

    private static final String QUERY_CARS = """
            query {
              cars {
                id
                licensePlateNumber
                manufacturer
                model
              }
            }
            """;

    private static final String QUERY_CAR_BY_ID = """
            query($carId: ID!) {
              carById(id: $carId) {
                id
                licensePlateNumber
                manufacturer
                model
              }
            }
            """;

    private final GraphQlClient graphQlClient;

    public InventoryGraphQLClient(@Value("${graphql.service.url}") String graphqlUrl) {
        WebClient webClient = WebClient.builder().baseUrl(graphqlUrl).build();
        this.graphQlClient = HttpGraphQlClient.create(webClient);
    }

    @Override
    public List<CarDto> findAllCars() {
        return Optional.ofNullable(graphQlClient.document(QUERY_CARS)
                                                .retrieve("cars")
                                                .toEntityList(CarDto.class)
                                                .block())
                        .orElseGet(List::of);
    }

    @Override
    public Optional<CarDto> findCarById(Long id) {
        return Optional.ofNullable(
                graphQlClient.document(QUERY_CAR_BY_ID)
                        .variable("carId", id)
                        .retrieveSync("carById")
                        .toEntity(CarDto.class)
        );
    }
}
