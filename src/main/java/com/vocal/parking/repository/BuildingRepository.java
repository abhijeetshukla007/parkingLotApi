package com.vocal.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vocal.parking.entity.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {

}
