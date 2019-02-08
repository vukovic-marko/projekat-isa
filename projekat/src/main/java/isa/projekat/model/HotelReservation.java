package isa.projekat.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "HOTEL_RESERVATION")
public class HotelReservation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5827876585861668384L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateOfArrival;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateOfDeparture;
	
	@Column(nullable = false)
	private Integer numberOfGuests;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "hotel_room_occupation", 
		joinColumns = 
		{
			@JoinColumn(name = "hotel_reservation_id", referencedColumnName = "id")		},
		inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
	private Set<HotelRoom> rooms;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "hotel_reservations_additional_services",
			joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName="id"))
	private Set<HotelAdditionalService> services;
	
	@Column(nullable = false)
	private Double price;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfArrival() {
		return dateOfArrival;
	}

	public void setDateOfArrival(Date dateOfArrival) {
		this.dateOfArrival = dateOfArrival;
	}

	public Date getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(Date dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}

	public Integer getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(Integer numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}
	
	public Set<HotelRoom> getRooms() {
		return rooms;
	}

	public void setRooms(Set<HotelRoom> rooms) {
		this.rooms = rooms;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<HotelAdditionalService> getServices() {
		return services;
	}

	public void setServices(Set<HotelAdditionalService> services) {
		this.services = services;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
