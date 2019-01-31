package isa.projekat.model;

import java.util.Set;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

public class RentACarCompany {
	private String name;
	private String description;
	private String address;
	@Id()
	private long id;
	@Version
	private long version;
	@OneToMany
	private Set<Destination> affilialites;
	
	
}
