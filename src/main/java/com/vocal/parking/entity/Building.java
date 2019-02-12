package com.vocal.parking.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Building")
public class Building {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int buildingId;
	@Column
	private String name;
	@Size(max = 5)
	@OneToMany(mappedBy = "building",cascade=CascadeType.ALL)
	private List<Floor> floors;
	@Column
	private boolean available=true;

	/**
	 * @return the id
	 */
	public int getId() {
		return buildingId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.buildingId = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the floors
	 */
	public List<Floor> getFloors() {
		return floors;
	}

	/**
	 * @param floors the floors to set
	 */
	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}

	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

}
