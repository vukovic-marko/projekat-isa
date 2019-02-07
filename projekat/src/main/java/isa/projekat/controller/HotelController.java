package isa.projekat.controller;

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
import isa.projekat.model.HotelReservationHelperClass;
import isa.projekat.model.HotelRoom;
import isa.projekat.model.HotelRoomPrice;
import isa.projekat.model.User;
import isa.projekat.repository.HotelAdditionalServiceRepository;
import isa.projekat.repository.HotelRepository;
import isa.projekat.repository.HotelReservationRepository;
import isa.projekat.repository.HotelRoomPriceRepository;
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
	
	@Autowired
	private HotelAdditionalServiceRepository additionalServiceRepository;
	
	@Autowired
	private HotelRoomPriceRepository roomPriceRepository;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Hotel> getHotels() {
		return hotelService.getHotels(); 
	}
	
	@RequestMapping(value="/{id}/additionalservices", method = RequestMethod.GET)
	public List<HotelAdditionalService> getAdditionalServices(@PathVariable Long id) {
		return hotelService.getAdditionalServices(id);
	}
	
//	@RequestMapping(value="/showavailablerooms/{hotelId}/{size}", method = RequestMethod.POST, 
//			consumes = MediaType.APPLICATION_JSON_VALUE)
//	public List<HotelRoom> showAvailableRooms(@RequestBody java.sql.Date[] dates, @PathVariable Long hotelId, @PathVariable Long size) {
//		System.out.println(size);
//		
//		Date dateOfArrival = (Date) dates[0];
//		Date dateOfDeparture = (Date) dates[1];
//		
//		List<HotelRoom> allRooms = hotelRepository.findOne(hotelId).getRooms();
//		List<HotelRoom> unavailableRooms = hotelRoomRepository.findUnavailableRooms(dateOfArrival, dateOfDeparture, size, hotelId);
//		System.out.println(unavailableRooms);
//		allRooms.removeAll(unavailableRooms);
//		
//		return allRooms;
//	}
	
	@RequestMapping(value="/showavailablerooms", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<HotelRoom>> showAvailableRooms(@RequestBody HotelReservationHelperClass query) {

//		System.out.println("PRISTIGLI UPIT");
//		System.out.println(query.getHotelId());
//		System.out.println(query.getDateOfArrival());
//		System.out.println(query.getDateOfDeparture());
//		System.out.println(query.getRoomConfigurations());
//		System.out.println(query.getNumberOfRooms());
		List<HotelRoom> ret = new ArrayList<HotelRoom>();
		
		if (query.getHotelId() == null ||
				query.getNumberOfRooms().size() == 0 || 
				query.getNumberOfRooms().size() != query.getRoomConfigurations().size() ||
				query.getDateOfArrival() == null || query.getDateOfDeparture() == null ||
				query.getDateOfArrival().compareTo(query.getDateOfDeparture()) >= 0)
			return new ResponseEntity<List<HotelRoom>>(ret, HttpStatus.BAD_REQUEST);
		
		List<HotelRoom> allRooms = hotelRepository.findOne(query.getHotelId()).getRooms();
		List<HotelRoom> unavailableRooms;
		List<HotelRoom> helper;
		
		for (int i = 0; i<query.getRoomConfigurations().size(); i++) {
			helper = new ArrayList<HotelRoom>(allRooms);
			unavailableRooms = hotelRoomRepository.findUnavailableRooms(query.getDateOfArrival(), 
					query.getDateOfDeparture(), query.getRoomConfigurations().get(i).longValue(), query.getHotelId());
			
			helper.removeAll(unavailableRooms);
			if (helper.size() < query.getNumberOfRooms().get(i)) {
				return new ResponseEntity<List<HotelRoom>>(new ArrayList<HotelRoom>(), HttpStatus.OK);
			} else {
				ret.addAll(helper);
			}
		}
				
		List<HotelRoom> retList = new ArrayList<HotelRoom>();
		
		for (HotelRoom r : ret) {
			for (HotelRoomPrice p : r.getRoomPrices()) {
				if (query.getDateOfArrival().compareTo(p.getStartDate()) >= 0 && query.getDateOfDeparture().compareTo(p.getEndDate()) <= 0)
					retList.add(r);
			}
		}
		
//		Date dateOfArrival = (Date) dates[0];
//		Date dateOfDeparture = (Date) dates[1];
//		
//		List<HotelRoom> allRooms = hotelRepository.findOne(hotelId).getRooms();
//		List<HotelRoom> unavailableRooms = hotelRoomRepository.findUnavailableRooms(dateOfArrival, dateOfDeparture, size, hotelId);
//		System.out.println(unavailableRooms);
//		allRooms.removeAll(unavailableRooms);
		
		return new ResponseEntity<List<HotelRoom>>(retList, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/addhotelreservation", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public @ResponseBody ResponseEntity<Boolean> addHotelReservation(@RequestBody HotelReservationHelperClass query, HttpServletRequest request) {
		
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		List<String> rooms = query.getRoomNumbers();
		Date dateOfArrival = query.getDateOfArrival();
		Date dateOfDeparture = query.getDateOfDeparture();
		
		//Boolean error = false;
		
		for (String roomNumber : rooms) {
			if (hotelRoomRepository.findRooms(dateOfArrival, dateOfDeparture, roomNumber, query.getHotelId()).size() != 0) {
				//error = true;
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		
		List<HotelAdditionalService> additionalServices = new ArrayList<HotelAdditionalService>();
		Double price = 0.0;
		for (Long l : query.getServices()) {
			HotelAdditionalService aS = additionalServiceRepository.findOne(l);
			if (aS == null) 
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);
			else {
				additionalServices.add(aS);
				price += aS.getPrice();
			}
		}
		
		//if (!error) {
		
		
		
		
		List<HotelRoom> r = new ArrayList<HotelRoom>();
		Hotel h = hotelRepository.findOne(query.getHotelId());
		HotelReservation res = new HotelReservation();
		res.setDateOfArrival(dateOfArrival);
		res.setDateOfDeparture(dateOfDeparture);
		res.setNumberOfGuests(-1);
		
		res.setUser(user);
		res.setServices(additionalServices);
		for (String roomNumber : rooms) {
			HotelRoom rr = hotelRoomRepository.findByRoomNumberAndHotel(roomNumber, h);
			HotelRoomPrice rp = roomPriceRepository.findPriceForRoomOnDate(dateOfArrival, rr);
			
			long diff = dateOfDeparture.getTime() - dateOfArrival.getTime();
			 
			int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			
			price += diffDays * rp.getPrice();
			//System.out.println("difference between days: " + diffDays);
			
			rr.getRoomReservations().add(res);
			r.add(rr);
		}
		
		
		res.setPrice(price);
		res.setRooms(r);

		hotelReservationRepository.save(res);
		hotelRoomRepository.save(r);
			
		//}	
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
//	@RequestMapping(value="/addr", method = RequestMethod.GET)
//	@PreAuthorize("hasRole('ROLE_USER')")
//	public List<HotelRoom> addReservation(HttpServletRequest request) {
//		String token = tokenUtils.getToken(request);
//		String username = this.tokenUtils.getUsernameFromToken(token);
//		User user = (User) this.userDetailsService.loadUserByUsername(username);
//		
//		HotelReservation r = new HotelReservation();
//				
//		try {
//			r.setDateOfArrival(new SimpleDateFormat("dd-MM-yyyy").parse("24-12-2018"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			r.setDateOfDeparture(new SimpleDateFormat("dd-MM-yyyy").parse("29-12-2018"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		HotelRoom room = hotelRoomRepository.findOne(1L);
//		
//		r.setNumberOfGuests(1);
//		r.setRooms(new ArrayList<HotelRoom>());
//		r.getRooms().add(room);
//		r.setUser(user);
//		
//		//room.getRoomReservations().add(r);
//		
//		
//		hotelReservationRepository.save(r);
//		hotelRoomRepository.save(room);
//		
//		List<HotelRoom> allRooms = hotelRoomRepository.findAll();
//		List<HotelRoom> zauzete = new ArrayList<HotelRoom>();
//		try {
//			zauzete = hotelRoomRepository.find(new SimpleDateFormat("dd-MM-yyyy").parse("29-12-2018"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println(zauzete);
//		System.out.println(zauzete.size());
//		System.out.println(allRooms);
//		System.out.println(allRooms.size());
//		System.out.println("*****************");
//		allRooms.removeAll(zauzete);
//		System.out.println(allRooms);
//		System.out.println(allRooms.size());
//		
//		return allRooms;
//	}
}
