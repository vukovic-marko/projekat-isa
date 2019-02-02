package isa.projekat.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
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
	@OneToMany(fetch=FetchType.EAGER,cascade= CascadeType.ALL,orphanRemoval = true)
	private Set<BranchOffice> branchOffices;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Destination location;
	
	public Set<BranchOffice> getBranchOffices() {
		return branchOffices;
	}
	public void setBranchOffices(Set<BranchOffice> branchOffices) {
		this.branchOffices = branchOffices;
	}
	public Destination getLocation() {
		return location;
	}
	public void setLocation(Destination location) {
		this.location = location;
	}
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="admin")
	@JsonIgnore
	private User admin;
	
	public User getAdmin() {
		return admin;
	}
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
		
}
