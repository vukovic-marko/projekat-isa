package isa.projekat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "HOTEL_ROOMS", 
	uniqueConstraints = {@UniqueConstraint(columnNames = {"hotel_id", "roomNumber"})})
public class HotelRoom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String roomNumber;
	
	@Column(nullable = false)
	private Long floorNumber;
	
	@Column(nullable = false)
	private Long size; // number of people
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hotel_id", nullable = false)
	private Hotel hotel;

	@JsonIgnore
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Long getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Long floorNumber) {
		this.floorNumber = floorNumber;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
}
