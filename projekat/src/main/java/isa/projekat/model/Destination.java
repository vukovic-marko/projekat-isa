package isa.projekat.model;

import javax.persistence.Id;

public class Destination {

	private String country;
	
	private String city;
	
	@Id
	private long id;

}