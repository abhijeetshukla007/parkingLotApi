package com.vocal.parking.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vocal.parking.entity.Slot;
import com.vocal.parking.entity.VehicleSize;

/**
 * @author abhij
 *
 */
public interface SlotRepository extends JpaRepository<Slot, Integer> {

	@Query("Select s from Slot s where available=true and s.floor.floorId =:floorId order by slotId ASC")
	List<Slot> findByFloorId(@Param("floorId") int floorId, Pageable pageable);

	List<Slot> findByRegistrationNumber(String registrationNo);

	List<Slot> findByColor(String color);

	List<Slot> findBySize( VehicleSize vehicleSize);
}
