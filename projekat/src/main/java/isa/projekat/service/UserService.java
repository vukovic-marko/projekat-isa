package isa.projekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import isa.projekat.model.User;
import isa.projekat.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
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
	public Boolean register(User u) {
		u.setActivated(false);
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		User ret = userRepository.save(u);
		if (ret != null) {
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

}
