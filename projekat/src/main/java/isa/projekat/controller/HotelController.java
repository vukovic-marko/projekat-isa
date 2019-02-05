package isa.projekat.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import isa.projekat.model.Hotel;
import isa.projekat.model.HotelAdditionalService;
import isa.projekat.model.HotelReservation;
import isa.projekat.model.HotelRoom;
import isa.projekat.model.User;
import isa.projekat.repository.HotelRepository;
import isa.projekat.repository.HotelReservationRepository;
import isa.projekat.repository.HotelRoomRepository;
import isa.projekat.security.TokenUtils;
import isa.projekat.service.CustomUserDetailsService;
import isa.projekat.service.HotelService;

@RestController
@RequestMapping(value="/hotel")
public class HotelController {
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private HotelRoomRepository hotelRoomRepository;
	
	@Autowired
	private HotelReservationRepository hotelReservationRepository;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Hotel> getHotels() {
		return hotelService.getHotels(); 
	}
	
	@RequestMapping(value="/{id}/additionalservices", method = RequestMethod.GET)
	public List<HotelAdditionalService> getAdditionalServices(@PathVariable Long id) {
		return hotelService.getAdditionalServices(id);
	}
	
	@RequestMapping(value="/showavailablerooms/{hotelId}/{size}", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<HotelRoom> showAvailableRooms(@RequestBody java.sql.Date[] dates, @PathVariable Long hotelId, @PathVariable Long size) {
		System.out.println(size);
		
		Date dateOfArrival = (Date) dates[0];
		Date dateOfDeparture = (Date) dates[1];
		
		List<HotelRoom> allRooms = hotelRepository.findOne(hotelId).getRooms();
		List<HotelRoom> unavailableRooms = hotelRoomRepository.findUnavailableRooms(dateOfArrival, dateOfDeparture, size, hotelId);
		System.out.println(unavailableRooms);
		allRooms.removeAll(unavailableRooms);
		
		return allRooms;
	}
	
	@RequestMapping(value="/addr", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<HotelRoom> addReservation(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		HotelReservation r = new HotelReservation();
				
		try {
			r.setDateOfArrival(new SimpleDateFormat("dd-MM-yyyy").parse("24-12-2018"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			r.setDateOfDeparture(new SimpleDateFormat("dd-MM-yyyy").parse("29-12-2018"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HotelRoom room = hotelRoomRepository.findOne(1L);
		
		r.setNumberOfGuests(1);
		r.setRooms(new ArrayList<HotelRoom>());
		r.getRooms().add(room);
		r.setUser(user);
		
		//room.getRoomReservations().add(r);
		
		
		hotelReservationRepository.save(r);
		hotelRoomRepository.save(room);
		
		List<HotelRoom> allRooms = hotelRoomRepository.findAll();
		List<HotelRoom> zauzete = new ArrayList<HotelRoom>();
		try {
			zauzete = hotelRoomRepository.find(new SimpleDateFormat("dd-MM-yyyy").parse("29-12-2018"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(zauzete);
		System.out.println(zauzete.size());
		System.out.println(allRooms);
		System.out.println(allRooms.size());
		System.out.println("*****************");
		allRooms.removeAll(zauzete);
		System.out.println(allRooms);
		System.out.println(allRooms.size());
		
		return allRooms;
	}
}
