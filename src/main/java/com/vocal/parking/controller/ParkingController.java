/**
 * 
 */
package com.vocal.parking.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vocal.parking.entity.Vehicle;
import com.vocal.parking.service.ParkingServiceImpl;

/**
 * @author Test User
 *
 */
@RestController
public class ParkingController {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ParkingController.class);

	@Autowired
	private ParkingServiceImpl parkingService;

	@GetMapping("/")
	private String greeting() {
		return "Hi from Parking";
	}

	@RequestMapping(value = "/park/vehicle/v1", method = RequestMethod.POST)
	private ResponseEntity<?> parkVehicle(@RequestBody Vehicle vehicle) {
		ResponseEntity responseEntity = null;
		try {
			String message = parkingService.parkVehicle(vehicle);
			responseEntity = new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while Parking", e.getMessage());
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/unpark/vehicle/v1", method = RequestMethod.POST)
	private ResponseEntity<?> unParkVehicle(@RequestBody Vehicle vehicle) {
		ResponseEntity responseEntity = null;
		try {
			String message = parkingService.unparkVehicle(vehicle);
			responseEntity = new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while UnParking", e.getMessage());
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	// @GetMapping(value = "/get/vehicle/regNo/v1")
	@RequestMapping(value = "/get/vehicle/regNo/v", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	private ResponseEntity<?> getVehicleByRegNo(@RequestParam("regNo") String regNo) {
		ResponseEntity responseEntity = null;
		try {
			Vehicle vehicle = parkingService.getVehicleByRegNo(regNo);
			if (vehicle != null) {
				responseEntity = new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>("Vehicle not found", HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Error while getVehicleByRegNo", e.getMessage());
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping(value = "/get/vehicles/color/v1")
	private ResponseEntity<?> getVehicleByColor(@RequestParam("color") String color) {
		ResponseEntity responseEntity = null;
		try {
			List<Vehicle> vehicles = parkingService.getVehicleBycolor(color);
			responseEntity = new ResponseEntity<List<Vehicle>>(vehicles, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while getting VehicleByColor", e.getMessage());
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping(value = "/get/vehicles/size/v1")
	private ResponseEntity<?> getVehicleBySize(@RequestParam("size") String size) {
		ResponseEntity responseEntity = null;
		try {
			List<Vehicle> vehicles = parkingService.getAllVehicleBySize(size);
			responseEntity = new ResponseEntity<List<Vehicle>>(vehicles, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while getting VehicleBySize", e.getMessage());
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping(value = "/get/vehicles/duration/v1")
	private ResponseEntity<?> getVehiclesByDuration(@RequestParam() int duration) {
		ResponseEntity responseEntity = null;
		try {
			List<Vehicle> vehicles = parkingService.getAllVehiclesByDuration(duration);
			responseEntity = new ResponseEntity<List<Vehicle>>(vehicles, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while getting VehicleBy Duration", e.getMessage());
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping(value = "/test/hysterix")
	private String hysterix(@RequestParam() int duration) {
		return parkingService.dependentServiceData();
	}

}
