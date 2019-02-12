package com.vocal.parking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vocal.parking.entity.Floor;

public interface FloorRepository extends JpaRepository<Floor, Integer> {

	@Query("Select f from Floor f where available=true")
	List<Floor> getFloorByavailable();

	@Query("Select f from Floor f where available=true and slotsAvailable >=:vehicleSize order by floorId ASC")
	List<Floor> checkAvailabilityBySize(@Param("vehicleSize") int vehicleSize);

}
