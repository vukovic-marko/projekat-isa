package isa.projekat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.CascadeType;
@Entity
public class BranchOffice {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column
	private String address;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Destination location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Destination getLocation() {
		return location;
	}

	public void setLocation(Destination location) {
		this.location = location;
	}

	
}
