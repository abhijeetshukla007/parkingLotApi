package com.vocal.parking.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.vocal.parking.entity.Building;
import com.vocal.parking.entity.Floor;
import com.vocal.parking.entity.Slot;
import com.vocal.parking.entity.Vehicle;
import com.vocal.parking.entity.VehicleSize;
import com.vocal.parking.repository.BuildingRepository;
import com.vocal.parking.repository.FloorRepository;
import com.vocal.parking.repository.SlotRepository;

@Service
public class ParkingServiceImpl {

	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private FloorRepository floorRepository;
	@Autowired
	private SlotRepository slotRepository;

	private static final Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

	public String parkVehicle(Vehicle vehicle) {

		Floor floor = isSpaceAvailable(vehicle);
		int vehicleSlots = getVehicleSlots(vehicle);
		if (null != floor) {
			Pageable top = new PageRequest(0, vehicleSlots);
			List<Slot> slots = slotRepository.findByFloorId(floor.getFloorId(), top);
			for (Slot slot : slots) {
				slot.setAvailable(false);
				slot.setFloor(floor);
				slot.setInTime(new Date(System.currentTimeMillis()));
				slot.setRegistrationNumber(vehicle.getRegistrationNo());
				slot.setSize(vehicle.getType());
				slot.setColor(vehicle.getColor());
			}
			slotRepository.saveAll(slots);
			floor.setSlotsAvailable(floor.getSlotsAvailable() - vehicleSlots);
			floorRepository.save(floor);
			return "parked successfully";
		} else {
			return "No Space available";
		}

	}

	public String unparkVehicle(Vehicle vehicle) {
		int vehicleSlots = getVehicleSlots(vehicle);
		List<Slot> slots = slotRepository.findByRegistrationNumber(vehicle.getRegistrationNo());
		if (slots.isEmpty()) {
			return "No vehicle found";
		}
		for (Slot slot : slots) {
			slot.setAvailable(false);
			slot.setInTime(null);
			slot.setRegistrationNumber(null);
			slot.setSize(null);
			slot.setColor(null);
		}
		slotRepository.saveAll(slots);
		Optional<Floor> floor = floorRepository.findById(slots.get(0).getFloor().getFloorId());
		if (floor.isPresent()) {
			floor.get().setSlotsAvailable(floor.get().getSlotsAvailable() + vehicleSlots);
			floorRepository.save(floor.get());
		}
		return "Unparked successfully";
	}

	public Vehicle getVehicleByRegNo(String regNo) {
		List<Slot> list = slotRepository.findByRegistrationNumber(regNo);
		if (list != null && !list.isEmpty()) {
			Slot slot = list.get(0);
			Vehicle vehicle = new Vehicle();
			vehicle.setColor(slot.getColor());
			vehicle.setRegistrationNo(slot.getRegistrationNumber());
			vehicle.setType(slot.getSize());
			vehicle.setSlot(String.valueOf(slot.getSlotId()));
			vehicle.setFloor(slot.getFloor().getName());
			return vehicle;
		}
		return null;
		// TODO Auto-generated method stub

	}

	public List<Vehicle> getVehicleBycolor(String color) {
		List<Slot> slots = slotRepository.findByColor(color);
		slots = slots.stream().filter(distinctByKey(s -> s.getRegistrationNumber())).collect(Collectors.toList());
		List<Vehicle> vehicles = new ArrayList<>();
		if (slots != null && !slots.isEmpty()) {
			for (Slot slot : slots) {
				Vehicle vehicle = new Vehicle();
				vehicle.setColor(slot.getColor());
				vehicle.setRegistrationNo(slot.getRegistrationNumber());
				vehicle.setType(slot.getSize());
				vehicle.setSlot(String.valueOf(slot.getSlotId()));
				vehicle.setFloor(slot.getFloor().getName());
				vehicles.add(vehicle);
			}
			return vehicles;
		}
		return null;
	}

	public List<Vehicle> getAllVehicleBySize(String size) {
		List<Slot> slots = slotRepository.findBySize(VehicleSize.valueOf(size));
		List<Vehicle> vehicles = new ArrayList<>();
		if (slots != null && !slots.isEmpty()) {
			for (Slot slot : slots) {
				Vehicle vehicle = new Vehicle();
				vehicle.setColor(slot.getColor());
				vehicle.setRegistrationNo(slot.getRegistrationNumber());
				vehicle.setType(slot.getSize());
				vehicle.setSlot(String.valueOf(slot.getSlotId()));
				vehicle.setFloor(slot.getFloor().getName());
				vehicles.add(vehicle);
			}
			return vehicles;
		}
		return null;
	}

	public List<Vehicle> getAllVehiclesByDuration(int duration) {
		// TODO Auto-generated method stub
		return null;
	}

	private int getVehicleSlots(Vehicle vehicle) {
		if (vehicle.getType().equals(VehicleSize.LARGE)) {
			return 4;
		}
		if (vehicle.getType().equals(VehicleSize.MEDIUM)) {
			return 2;
		}
		if (vehicle.getType().equals(VehicleSize.SMALL)) {
			return 1;
		}
		return 0;
	}

	private Floor isSpaceAvailable(Vehicle vehicle) {
		List<Floor> floors = null;

		if (vehicle.getType().equals(VehicleSize.LARGE)) {
			floors = floorRepository.checkAvailabilityBySize(4);
		}
		if (vehicle.getType().equals(VehicleSize.MEDIUM)) {
			floors = floorRepository.checkAvailabilityBySize(2);
		}
		if (vehicle.getType().equals(VehicleSize.SMALL)) {
			floors = floorRepository.checkAvailabilityBySize(1);
		}
		if (floors != null && !floors.isEmpty()) {
			return floors.get(0);
		}
		return null;
	}

	@PostConstruct
	void initializeData() {

		Building building = new Building();
		// building.setId(1);
		building.setAvailable(true);
		List<Floor> floors = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Floor floor = new Floor();
			floor.setAvailable(true);
			floor.setName(i + "-floor");
			// floor.setFloorId(i);
			List<Slot> slots = new ArrayList<>();
			for (int j = 0; j < 12; j++) {
				Slot slot = new Slot();
				slot.setFloor(floor);
				slots.add(slot);
			}
			// slotRepository.saveAll(slots);
			floor.setSlots(slots);
			floor.setBuilding(building);
			floors.add(floor);
		}
		//
		building.setFloors(floors);
		buildingRepository.save(building);
		// floorRepository.saveAll(floors);

	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public String dependentServiceData() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("http://localhost:8090/test", String.class);
	}

	String fallback() {
		return "fallback reponse";
	}

}
