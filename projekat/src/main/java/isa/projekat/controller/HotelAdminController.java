package isa.projekat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isa.projekat.model.Destination;
import isa.projekat.model.Hotel;
import isa.projekat.model.User;
import isa.projekat.repository.DestinationRepository;
import isa.projekat.repository.HotelRepository;
import isa.projekat.security.TokenUtils;
import isa.projekat.service.CustomUserDetailsService;
import isa.projekat.service.HotelAdminService;
import isa.projekat.service.UserService;

@RestController
@RequestMapping(value="/hoteladmin")
public class HotelAdminController {
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private DestinationRepository destinationRepository;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private HotelAdminService hotelAdminService;
	
	@RequestMapping(value="/edit/profile", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	public @ResponseBody ResponseEntity<Boolean> editProfile(@RequestBody Hotel h, HttpServletRequest request) {
		System.out.println("*********************");
		System.out.println(h.getName());
		System.out.println(h.getAddress());
		System.out.println(h.getPromoDescription());
		System.out.println("---------------------");
		System.out.println(h.getDestination().getCountry());
		System.out.println(h.getDestination().getCity());
		System.out.println("*********************");
		
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
//		System.out.println("*********************");
//		System.out.println("**** STARI HOTEL ****");
//		System.out.println(user.getHotel().getName());
//		System.out.println(user.getHotel().getAddress());
//		System.out.println(user.getHotel().getPromoDescription());
//		System.out.println("---------------------");
//		System.out.println(user.getHotel().getDestination().getCountry());
//		System.out.println(user.getHotel().getDestination().getCity());
//		System.out.println("*********************");
		
		Boolean edit = hotelAdminService.editHotel(user, h);
		
//		Destination destination = destinationRepository.getOne(user.getHotel().getDestination().getId());
//		destination.setCity(h.getDestination().getCity());
//		destination.setCountry(h.getDestination().getCountry());
//		
//		destinationRepository.save(destination);
		
//		Hotel hotel = hotelRepository.getOne(user.getHotel().getId());
//		hotel.setName(h.getName());
//		hotel.setAddress(h.getAddress());
//		hotel.setPromoDescription(h.getPromoDescription());
//		hotel.setDestination(destination);
//		
//		hotelRepository.save(hotel);
		
		return new ResponseEntity<Boolean>(edit, HttpStatus.OK);
	}
	
	@RequestMapping(value="/details", method=RequestMethod.GET)
	public Hotel getDetails(HttpServletRequest request) {		
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		if (user.getHotel() == null)
			user.setHotel(new Hotel());
		
		return user.getHotel();
	}
	
}
