package isa.projekat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isa.projekat.common.DeviceProvider;
import isa.projekat.model.Authority;
import isa.projekat.model.User;
import isa.projekat.model.UserTokenState;
import isa.projekat.security.TokenUtils;
import isa.projekat.security.auth.JwtAuthenticationRequest;
import isa.projekat.service.CustomUserDetailsService;
import isa.projekat.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Boolean> register(@RequestBody User u) {
		boolean reg = userService.register(u, null);
		return new ResponseEntity<Boolean>(reg, HttpStatus.OK);
	}

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private DeviceProvider deviceProvider;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response, Device device,
			HttpSession session) throws AuthenticationException, IOException {

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		// Ubaci username + password u kontext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token
		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername(), device);
		int expiresIn = tokenUtils.getExpiredIn(device);
		session.setAttribute("user", user.getUsername());
		// Vrati token kao odgovor na uspesno autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST) 
	public ResponseEntity<Boolean> changePassword(@RequestBody User u, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		Boolean changed = userService.changePassword(u.getPassword(), user);
		
		return new ResponseEntity<Boolean>(changed, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);

		Device device = deviceProvider.getCurrentDevice(request);

		if (this.tokenUtils.canTokenBeRefreshed(token, null)) {
			String refreshedToken = tokenUtils.refreshToken(token, device);
			int expiresIn = tokenUtils.getExpiredIn(device);

			return ResponseEntity.ok(new isa.projekat.model.UserTokenState(refreshedToken, expiresIn));
		} else {
			UserTokenState userTokenState = new UserTokenState();
			return ResponseEntity.badRequest().body(userTokenState);
		}
	}

	@GetMapping(value = "/checkemail")
	public Boolean checkEmail(@RequestParam(name = "email") String email) {
		return userService.checkEmail(email);
	}

	@GetMapping(value = "/checkusername")
	public Boolean checkUsername(@RequestParam(name = "username") String username) {
		return userService.checkUsername(username);
	}
	
	@GetMapping(value = "/checkactivated") 
	public Boolean checkActivated(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		return user.getActivated();
	}

	@RequestMapping(value = "/activate/{username}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Boolean> activate(@PathVariable String username) {
		boolean reg = userService.activate(username);
		return new ResponseEntity<Boolean>(reg, HttpStatus.OK);
	}
}
