package isa.projekat.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

import isa.projekat.model.Destination;
import isa.projekat.model.Hotel;
import isa.projekat.model.HotelAdditionalService;
import isa.projekat.model.HotelReservation;
import isa.projekat.model.HotelReservationHelperClass;
import isa.projekat.model.HotelRoom;
import isa.projekat.model.HotelRoomPrice;
import isa.projekat.model.User;
import isa.projekat.repository.DestinationRepository;
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
	private HotelService hotelService;
	
	@Autowired
	private HotelRoomRepository hotelRoomRepository;
	
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
	
	@RequestMapping(value="/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> searchHotels(@RequestBody HotelReservationHelperClass query) {
				
		Hotel h = new Hotel();
		h.setName(query.getHotel().getName());
		h.setAddress(query.getHotel().getAddress());
		Destination d = new Destination();
		d.setCity(query.getHotel().getDestination().getCity());
		d.setCountry(query.getHotel().getDestination().getCountry());
		h.setDestination(d);
		


		ExampleMatcher matcher = ExampleMatcher.matching()
		  .withMatcher("name", match -> match.contains())
		  .withMatcher("address", match -> match.contains())
		  .withMatcher("destination.city", match -> match.contains())
		  .withMatcher("destination.country", match -> match.contains());
		
		Example<Hotel> example = Example.of(h, matcher); 
		
		List<Hotel> hots = hotelRepository.findAll(example);
		List<Hotel> ret = new ArrayList<>(hots);
		List<Hotel> retList = new ArrayList<Hotel>();

		if (query.getDateOfArrival() != null && query.getDateOfDeparture() != null) {
			// obrisi one hotele koji nemaju slobodnu sobu
			Date dateOfArrival = query.getDateOfArrival();
			Date dateOfDeparture = query.getDateOfDeparture();

			for (Hotel hotel : hots) {
				List<HotelRoom> allRooms = hotel.getRooms();
				List<HotelRoom> unavailableRooms = new ArrayList<HotelRoom>();
				unavailableRooms.addAll(hotelRoomRepository.findUnavailableRooms(dateOfArrival, 
						dateOfDeparture, 1L, hotel.getId()));

				allRooms.removeAll(unavailableRooms);
				System.out.println(allRooms.size());
				if (allRooms.size() == 0) {
					System.out.println("izbacuje se hotel " + hotel.getId());
					ret.remove(hotel);
					//continue;
				} else {
					for (HotelRoom help : allRooms) {
						for (HotelRoomPrice p : help.getRoomPrices()) {
							if (query.getDateOfArrival().compareTo(p.getStartDate()) >= 0 && query.getDateOfDeparture().compareTo(p.getEndDate()) <= 0)
								retList.add(hotel);
						}
					}
				}	
			}
	
		} else {
			retList.addAll(hots);
		}
		
		
		return retList;
	}
	
	@RequestMapping(value="/showavailablerooms", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<HotelRoom>> showAvailableRooms(@RequestBody HotelReservationHelperClass query) {

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
		
		return new ResponseEntity<List<HotelRoom>>(retList, HttpStatus.OK);
	}
}
