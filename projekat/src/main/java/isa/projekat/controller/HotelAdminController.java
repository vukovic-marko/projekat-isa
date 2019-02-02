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
import isa.projekat.model.HotelRoom;
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
		
		if (h.getName().equals("") || h.getName() == null ||
				h.getAddress().equals("") || h.getAddress() == null ||
				h.getPromoDescription().equals("") || h.getPromoDescription() == null ||
				h.getDestination() == null || h.getDestination().getCity().equals("") || h.getDestination().getCity() == null ||
				h.getDestination().getCountry().equals("") || h.getDestination().getCountry() == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		
		Boolean edit = hotelAdminService.editHotel(user, h);		
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
	
	@RequestMapping(value="/rooms", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<HotelRoom>> getRooms(HttpServletRequest request) {
		System.out.println("pogodio");
		
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		List<HotelRoom> lista = new ArrayList<HotelRoom>();
		
		if (user.getHotel() == null)
			return new ResponseEntity<List<HotelRoom>>(lista, HttpStatus.BAD_REQUEST);
		
		lista = user.getHotel().getRooms();
		
		return new ResponseEntity<List<HotelRoom>>(lista, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addroom", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Boolean> addRoom(@RequestBody HotelRoom room, HttpServletRequest request) {
		
		System.out.println("-----------------");
		System.out.println(room.getRoomNumber());
		System.out.println(room.getFloorNumber());
		System.out.println(room.getSize());
		
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		if (user.getHotel() == null || user.getHotel().getRooms() == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		Boolean success = hotelAdminService.addRoom(user.getHotel(), room);
		
		return new ResponseEntity<Boolean>(success, HttpStatus.OK);
	}
}
