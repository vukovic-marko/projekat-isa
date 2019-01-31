package isa.projekat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isa.projekat.model.User;
import isa.projekat.repository.UserRepository;
import isa.projekat.security.TokenUtils;
import isa.projekat.service.CustomUserDetailsService;
@RestController
@RequestMapping(value="/racadmin")
public class RentACarAdminController {
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@GetMapping(value = "/i",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User whoami(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
	if(token==null)
		return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
	    User user = (User) this.userDetailsService.loadUserByUsername(uname);
	    return user;
	   
	}
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@PutMapping( consumes = MediaType.APPLICATION_JSON_VALUE, value = "/edit",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public boolean edit(HttpServletRequest request,@RequestBody User u) {
		String token = tokenUtils.getToken(request);
	if(token==null)
		return false;
		String uname = this.tokenUtils.getUsernameFromToken(token);
	    
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if(u.getCity()==null||u.getCity().equals(""))
			   return false;
		if(u.getPhone()==null||u.getPhone().equals(""))
			   return false;
		if(u.getFirstName()==null||u.getFirstName().equals(""))
			   return false;
		if(u.getLastName()==null||u.getLastName().equals(""))
			   return false;
		user.setCity(u.getCity());
	    user.setFirstName(u.getFirstName());
	    user.setLastName(u.getLastName());
	    user.setPhone(u.getPhone());
	    userRepository.save(user);
	    return true;
	}
}
