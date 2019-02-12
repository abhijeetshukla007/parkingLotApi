/**
 * 
 */
package com.vocal.parking.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @author abhij
 *
 */
@Entity
@Table(name = "Floor")
public class Floor {

	@Id
	@Column(name = "floorId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int floorId;
	@Column
	private String name;
	@Size(max = 12)
	@OneToMany(mappedBy = "floor",cascade=CascadeType.ALL)
	private List<Slot> slots;
	@Column
	private int slotsAvailable=12;

	/**
	 * @return the slotsAvailable
	 */
	public int getSlotsAvailable() {
		return slotsAvailable;
	}

	/**
	 * @param slotsAvailable the slotsAvailable to set
	 */
	public void setSlotsAvailable(int slotsAvailable) {
		this.slotsAvailable = slotsAvailable;
	}

	/**
	 * @return the building
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * @param building the building to set
	 */
	public void setBuilding(Building building) {
		this.building = building;
	}

	@Column
	private boolean available = true;

	@ManyToOne
	@JoinColumn(name = "buildingId")
	private Building building;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the floorId
	 */
	public int getFloorId() {
		return floorId;
	}

	/**
	 * @param floorId the floorId to set
	 */
	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the slots
	 */
	public List<Slot> getSlots() {
		return slots;
	}

	/**
	 * @param slots the slots to set
	 */
	public void setSlots(List<Slot> slots) {
		this.slots = slots;
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
