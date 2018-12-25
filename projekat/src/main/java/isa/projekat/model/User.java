package isa.projekat.model;

import javax.persistence.*;

@Entity
public class User {

	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String lastName;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

}
