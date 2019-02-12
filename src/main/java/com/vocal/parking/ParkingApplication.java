package com.vocal.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class ParkingApplication {
	public static void main(String[] args) {
		SpringApplication.run(ParkingApplication.class, args);
	}
}
