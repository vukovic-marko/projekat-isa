package isa.projekat.model;

import java.util.List;

public class HotelReservationHelperClass {
	
	private List<Integer> roomConfigurations;
	
	private List<Integer> numberOfRooms;
	
	private List<String> roomNumbers;
	
	private List<Long> services;
	
	private Long hotelId;
	
	private java.sql.Date dateOfArrival;
	
	private java.sql.Date dateOfDeparture;
	
	private Hotel hotel;

	public List<Integer> getRoomConfigurations() {
		return roomConfigurations;
	}

	public void setRoomConfigurations(List<Integer> roomConfigurations) {
		this.roomConfigurations = roomConfigurations;
	}

	public List<Integer> getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(List<Integer> numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public List<String> getRoomNumbers() {
		return roomNumbers;
	}

	public void setRoomNumbers(List<String> roomNumbers) {
		this.roomNumbers = roomNumbers;
	}

	public List<Long> getServices() {
		return services;
	}

	public void setServices(List<Long> services) {
		this.services = services;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public java.sql.Date getDateOfArrival() {
		return dateOfArrival;
	}

	public void setDateOfArrival(java.sql.Date dateOfArrival) {
		this.dateOfArrival = dateOfArrival;
	}

	public java.sql.Date getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(java.sql.Date dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
}
