package isa.projekat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import isa.projekat.model.RegisteredUser.Friendship;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6882427434891133050L;


	//------------------------------------
	//AIRPORT ADMIN


	//-----------------------------------

	//------------------------------------
	//REGISTERED USER
	@OneToMany(mappedBy = "friendRequester")
	private Set<Friendship> requestedFriends;

	@OneToMany(mappedBy = "friendReceiver")
	private Set<Friendship> receivedFriends;
	//-----------------------------------


	//------------------------------------
	//RENTA A CAR ADMINISTRATOR
	@OneToOne
	private RentACarCompany rentACarCompany;
	public RentACarCompany getRentACarCompany() {
		return rentACarCompany;
	}

	public void setRentACarCompany(RentACarCompany rentACarCompany) {
		this.rentACarCompany = rentACarCompany;
	}
	//-----------------------------------
	@Version
	private Long version;
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority> authorities;

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String lastName;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private Boolean activated;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String phone;

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		if (getAuthorities().iterator().next().getAuthority().equals("ROLE_USER"))
			return getActivated();
		else
			return true;
	}

	

}
