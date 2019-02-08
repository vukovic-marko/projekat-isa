package isa.projekat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CarReview {
	@Column
	private Integer carReview;
	@Column
	private Integer companyReview;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private CarReservation carReservation;

	public Integer getCarReview() {
		return carReview;
	}

	public void setCarReview(Integer carReview) {
		this.carReview = carReview;
	}

	public Integer getCompanyReview() {
		return companyReview;
	}

	public void setCompanyReview(Integer companyReview) {
		this.companyReview = companyReview;
	}

	public CarReservation getCarReservation() {
		return carReservation;
	}

	public void setCarReservation(CarReservation carReservation) {
		this.carReservation = carReservation;
	}
	
	
	
	
}
