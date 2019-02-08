package isa.projekat.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "HOTEL")
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String address;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "hotel")
	private List<HotelRoom> rooms;
	
	@OneToMany(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			mappedBy = "hotel")
	private List<HotelAdditionalService> additionalServices;
	
//	@OneToOne(fetch = FetchType.EAGER, optional=false)
//	@JoinColumn(name="address_id", nullable=false)
	@ManyToOne(fetch = FetchType.EAGER, optional=false)
    @JoinColumn(name = "address_id", nullable=false)
	private Destination destination;
	
	public Destination getDestination() {
		return destination;
	}
	
	@JsonIgnore
	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	@Column(nullable = false)
	private String promoDescription;
	
	public Long getId() {
		return id;
	}
	
	@OneToOne(fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="admin_id", nullable=false)
	public User admin;


	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}

	@JsonIgnore
	public User getHotelAdministrator() {
		return admin;
	}

	public void setHotelAdministrator(User admin) {
		this.admin = admin;
	}

	public List<HotelRoom> getRooms() {
		return rooms;
	}

	public void setRooms(List<HotelRoom> rooms) {
		this.rooms = rooms;
	}

	public List<HotelAdditionalService> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(List<HotelAdditionalService> additionalServices) {
		this.additionalServices = additionalServices;
	}
}