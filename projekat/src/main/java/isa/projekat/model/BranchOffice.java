package isa.projekat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class BranchOffice {
	@Id
	private long id;

	@Column
	private String address;
	
	@ManyToOne
	private Destination destination;
	
}
