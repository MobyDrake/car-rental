package com.mobydrake.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ReservationServiceApplication {

	static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}

}
