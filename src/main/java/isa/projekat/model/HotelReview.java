package isa.projekat.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class HotelReview {
	@Column
	private Integer hotelReview;

	@OneToMany(cascade=CascadeType.ALL)
	private List<RoomReview> roomReviews;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne(cascade=CascadeType.ALL)
	private HotelReservation hotelReservation;

	public Integer getHotelReview() {
		return hotelReview;
	}

	public void setHotelReview(Integer hotelReview) {
		this.hotelReview = hotelReview;
	}

	public List<RoomReview> getRoomReviews() {
		return roomReviews;
	}

	public void setRoomReviews(List<RoomReview> roomReviews) {
		this.roomReviews = roomReviews;
	}

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


}
