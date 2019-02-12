/**
 * 
 */
package com.vocal.parking.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author abhij
 *
 */
@Entity
@Table(name = "Slot")
public class Slot {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "slotId")
	private int slotId;
	@Column
	private VehicleSize size;
	@Column
	private String registrationNumber;
	@Column
	private boolean available = true;
	@Column
	private Date inTime;
	@Column
	private String color;
	
	@ManyToOne
	@JoinColumn(name = "floorId")
	private Floor floor;

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the slotId
	 */
	public int getSlotId() {
		return slotId;
	}

	/**
	 * @param slotId the slotId to set
	 */
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	/**
	 * @return the floor
	 */
	public Floor getFloor() {
		return floor;
	}

	/**
	 * @param floor the floor to set
	 */
	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	/**
	 * @return the size
	 */
	public VehicleSize getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(VehicleSize size) {
		this.size = size;
	}

	/**
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * @param registrationNumber the registrationNumber to set
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
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

	/**
	 * @return the inTime
	 */
	public Date getInTime() {
		return inTime;
	}

	/**
	 * @param inTime the inTime to set
	 */
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

}
