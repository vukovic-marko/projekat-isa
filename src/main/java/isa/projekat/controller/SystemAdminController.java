package isa.projekat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isa.projekat.model.User;
import isa.projekat.security.TokenUtils;
import isa.projekat.service.UserService;

@RestController
@RequestMapping(value="/sysadmin")
public class SystemAdminController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/register/{role}", 
			method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public @ResponseBody ResponseEntity<Boolean> register(@PathVariable Integer role, @RequestBody User u) {
		boolean reg = userService.register(u, role);
		return new ResponseEntity<Boolean>(reg, HttpStatus.OK);
		
	}
}
