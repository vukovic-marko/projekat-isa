package isa.projekat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isa.projekat.model.Hotel;
import isa.projekat.model.HotelAdditionalService;
import isa.projekat.model.HotelRoom;
import isa.projekat.model.HotelRoomPrice;
import isa.projekat.model.User;
import isa.projekat.security.TokenUtils;
import isa.projekat.service.CustomUserDetailsService;
import isa.projekat.service.HotelAdminService;

@RestController
@RequestMapping(value="/hoteladmin")
public class HotelAdminController {
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private HotelAdminService hotelAdminService;
	
	@RequestMapping(value="/remove/room/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	public Boolean removeRoom(@PathVariable("id") Long id) {
		return hotelAdminService.removeRoom(id);
	}
	
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
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	public Hotel getDetails(HttpServletRequest request) {		
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		if (user.getHotel() == null)
			user.setHotel(new Hotel());
		
		return user.getHotel();
	}
	
	@RequestMapping(value="/rooms", method=RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	public @ResponseBody ResponseEntity<List<HotelRoom>> getRooms(HttpServletRequest request) {
		//System.out.println("pogodio");
		
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		List<HotelRoom> lista = new ArrayList<HotelRoom>();
		
		if (user.getHotel() == null)
			return new ResponseEntity<List<HotelRoom>>(lista, HttpStatus.BAD_REQUEST);
		
		lista = user.getHotel().getRooms();
		
		return new ResponseEntity<List<HotelRoom>>(lista, HttpStatus.OK);
	}
	
	@RequestMapping(value="/room/prices", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	public @ResponseBody ResponseEntity<List<HotelRoomPrice>> getRoomPrices(@RequestBody HotelRoom r, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		List<HotelRoomPrice> lista = new ArrayList<HotelRoomPrice>();
		
		if (user.getHotel() == null)
			return new ResponseEntity<List<HotelRoomPrice>>(lista, HttpStatus.BAD_REQUEST);
		
		lista = hotelAdminService.getRoomPrices(user.getHotel(), r);
		
		return new ResponseEntity<List<HotelRoomPrice>>(lista, HttpStatus.OK);
	}
	
	@RequestMapping(value="/additionalservices", method=RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	public @ResponseBody ResponseEntity<List<HotelAdditionalService>> getAdditionalServices(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		List<HotelAdditionalService> lista = new ArrayList<HotelAdditionalService>();
		
		if (user.getHotel() == null) 
			return new ResponseEntity<List<HotelAdditionalService>>(lista, HttpStatus.BAD_REQUEST);
		
		lista = user.getHotel().getAdditionalServices();
		
		return new ResponseEntity<List<HotelAdditionalService>>(lista, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addservice", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	public @ResponseBody ResponseEntity<Boolean> addService(@RequestBody HotelAdditionalService service, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		if (user.getHotel() == null || user.getHotel().getRooms() == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		Boolean success = hotelAdminService.addService(user.getHotel(), service);
		
		return new ResponseEntity<Boolean>(success, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/addroom", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
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
	
	@GetMapping(value = "/checkstartdate/{roomNumber}")
	public Boolean checkStartDate(@RequestParam(name = "startdate") java.sql.Date startDate, @PathVariable String roomNumber, 
			HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		return hotelAdminService.checkDate(startDate, roomNumber, user.getHotel().getId());
	}
	
	@GetMapping(value = "/checkenddate/{roomNumber}")
	public Boolean checkEndDate(@RequestParam(name = "enddate") java.sql.Date endDate, @PathVariable String roomNumber,
			HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		return hotelAdminService.checkDate(endDate, roomNumber, user.getHotel().getId());
	}
	
	@RequestMapping(value="/addroomprice/{roomNumber}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	public @ResponseBody ResponseEntity<Boolean> addRoomPrice(@RequestBody HotelRoomPrice price, 
			@PathVariable String roomNumber, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
				
		if (hotelAdminService.checkDate(price.getStartDate(), roomNumber, user.getHotel().getId()) == false)
			return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
		
		if (hotelAdminService.checkDate(price.getEndDate(), roomNumber, user.getHotel().getId()) == false)
			return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
		
		if (price.getStartDate().compareTo(price.getEndDate()) >= 0)
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);
		
		System.out.println("----------------------------------");
		System.out.println(roomNumber + " <-------------------");
		System.out.println("----------------------------------");
		
		if (user.getHotel() == null || user.getHotel().getRooms() == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		Boolean b = hotelAdminService.addRoomPrice(user.getHotel(), price, roomNumber);
		
		return new ResponseEntity<Boolean>(b, HttpStatus.OK);
	}
}
