package isa.projekat.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class CarReservation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	private Date startDate;
	@Column(nullable=false)
	private Date endDate;
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	private Car car;
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	private User user;
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	private Destination startDestination;
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	private Destination endDestination;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
