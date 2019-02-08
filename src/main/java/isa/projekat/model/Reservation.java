package isa.projekat.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private HotelReservation hotelReservation;
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private CarReservation carReservation;
	/**
	 * dodati za let.....
	 */
	@ManyToOne
	private User user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public HotelReservation getHotelReservation() {
		return hotelReservation;
	}
	public void setHotelReservation(HotelReservation hotelReservation) {
		this.hotelReservation = hotelReservation;
	}
	public CarReservation getCarReservation() {
		return carReservation;
	}
	public void setCarReservation(CarReservation carReservation) {
		this.carReservation = carReservation;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
