package isa.projekat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import isa.projekat.model.Authority;
import isa.projekat.model.User;
import isa.projekat.repository.AuthorityRepository;
import isa.projekat.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;	
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	// proverava da li postoji korisnik sa zadatom e-mail adresom
	@Transactional(readOnly=true,isolation=Isolation.READ_COMMITTED)
	public Boolean checkEmail(String email) {
		return userRepository.findOneByEmail(email) == null;
	}
	
	//proverava jedinstvenost username-a
	@Transactional(readOnly=true,isolation=Isolation.READ_COMMITTED)
	public Boolean checkUsername(String username) {
		return userRepository.findOneByUsername(username) == null;
	}
	
	//registruje novog korisnika
	@Transactional(readOnly=false,isolation=Isolation.READ_COMMITTED)
	public Boolean register(User u, Integer i) {
		u.setActivated(false);
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		ArrayList<Authority> lista = new ArrayList<Authority>();
		if (i == null)	
			lista.add(authorityRepository.findOneByName("ROLE_USER"));
		else
			lista.add(authorityRepository.findOne(i.longValue()));
		u.setAuthorities(lista);
		User ret = userRepository.save(u);
		if (ret != null) {
			if (i == null)
				emailService.sendActivationEmail(ret);
			return true;
		}
		return false;
	}
	
	//aktivira korisnicki nalog
	@Transactional(readOnly=false,isolation=Isolation.READ_COMMITTED)
	public Boolean activate(String username) {
		User u=userRepository.findOneByUsername(username);
		if(u==null)
			return false;
		u.setActivated(true);
		return true;
	}
	
	//menja lozinku
	@Transactional(readOnly=false,isolation=Isolation.READ_COMMITTED)
	public Boolean changePassword(String newPassword, User u) {
		User user=userRepository.findOneByUsername(u.getUsername());
		if(user==null)
			return false;
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setActivated(true);
		return true;
	}
	
	//dobavlja sve korisnike iz baze
	@Transactional(readOnly=true,isolation=Isolation.READ_COMMITTED)
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	//dobavlja sve uloge iz baze
	public List<Authority> getAuthorities() {
		return authorityRepository.findAll();
	}
	
	//dobavlja ulogu iz baze po nazivu
	public Authority getAuthority(String name) {
		return authorityRepository.findOneByName(name);
	}
}
