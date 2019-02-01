package isa.projekat.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
@Entity
public class RentACarCompany {
	@Column(nullable=false)
	private String name;
	@Column
	private String description;
	@Column(nullable=false)
	private String address;
	@Id
	private long id;
	@Version
	private long version;
	@OneToMany
	private Set<Destination> affilialites;
	
	
	
	
}
