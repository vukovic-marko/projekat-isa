package isa.projekat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import isa.projekat.model.Destination;
import isa.projekat.model.Hotel;
import isa.projekat.model.HotelAdditionalService;
import isa.projekat.model.HotelReservation;
import isa.projekat.model.HotelRoom;
import isa.projekat.model.HotelRoomPrice;
import isa.projekat.model.User;
import isa.projekat.repository.DestinationRepository;
import isa.projekat.repository.HotelAdditionalServiceRepository;
import isa.projekat.repository.HotelRepository;
import isa.projekat.repository.HotelReservationRepository;
import isa.projekat.repository.HotelRoomPriceRepository;
import isa.projekat.repository.HotelRoomRepository;
import isa.projekat.repository.UserRepository;

@Service
public class HotelAdminService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private HotelRoomRepository hotelRoomRepository;
	
	@Autowired
	private HotelAdditionalServiceRepository hotelAdditionalServiceRepository;
	
	@Autowired
	private HotelRoomPriceRepository hotelRoomPriceRepository;
	
	@Autowired
	private DestinationRepository destinationRepository;
	
	@Autowired
	private HotelReservationRepository hotelReservationRepository;
	
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED)
	public Boolean removeRoom(Long id) {
		HotelRoom room = hotelRoomRepository.findOne(id);
		System.out.println(room.getId());
		
		Set<HotelReservation> res = hotelReservationRepository.findByRoom(id);
		if (res != null) {
			System.out.println(res.size());
			if (res.size() == 0) {
				hotelRoomRepository.delete(room);
				return true;
			} else {
				return false;
			}		
		}
		return null;
	}
	
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	public List<HotelRoomPrice> getRoomPrices(Hotel h, HotelRoom r) {
		Hotel hotel = hotelRepository.findOne(h.getId());
		
		List<HotelRoomPrice> ret = new ArrayList<HotelRoomPrice>();
		
		HotelRoom room = hotelRoomRepository.findByRoomNumberAndHotel(r.getRoomNumber(), hotel);
		
		//ret = (ArrayList<HotelRoomPrice>) room.getRoomPrices();
		ret = hotelRoomPriceRepository.findAllByHotelRoom(room);
		
		return ret;
	}
	
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED)
	public Boolean addRoom(Hotel h, HotelRoom r) {
		Hotel hotel = hotelRepository.getOne(h.getId());
		
		if (hotelRoomRepository.findByRoomNumberAndHotel(r.getRoomNumber(), hotel) != null)
			return false;

		hotel.getRooms().add(r);
		r.setHotel(hotel);
		
		hotelRoomRepository.save(r);
		hotelRepository.save(hotel);
	
		return true;
	}
	
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED)
	public Boolean addRoomPrice(Hotel h, HotelRoomPrice price, String roomNumber) {
		Hotel hotel = hotelRepository.getOne(h.getId());
		HotelRoom room = hotelRoomRepository.findByRoomNumberAndHotel(roomNumber, hotel);
				
		if (room == null)
			return false;
		
		room.getRoomPrices().add(price);
		price.setHotelRoom(room);
		
		hotelRoomPriceRepository.save(price);
		hotelRoomRepository.save(room);
		//hotelRepository.save(hotel);
		
		return true;
	}
	
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED)
	public Boolean addService(Hotel h, HotelAdditionalService s) {
		
		Hotel hotel = hotelRepository.getOne(h.getId());
		
		hotel.getAdditionalServices().add(s);
		
		s.setHotel(hotel);
		
		hotelAdditionalServiceRepository.save(s);
		
		hotelRepository.save(hotel);
		
		return true;
		
	}
	
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED)
	public Boolean editHotel(User u, Hotel h) {
		if (u.getHotel() == null) {
			Destination destination = destinationRepository.findByCountryAndCity(h.getDestination().getCountry(), h.getDestination().getCity());
			if (destination == null) {
				destination = new Destination();
				destination.setCity(h.getDestination().getCity());
				destination.setCountry(h.getDestination().getCountry());
				destinationRepository.save(destination);
			}
			
			Hotel hotel = new Hotel();
			hotel.setName(h.getName());
			hotel.setAddress(h.getAddress());
			hotel.setPromoDescription(h.getPromoDescription());
			hotel.setDestination(destination);
			hotel.setAdmin(u);
			hotel.setRooms(new ArrayList<HotelRoom>());
			hotel.setAdditionalServices(new ArrayList<HotelAdditionalService>());
			hotelRepository.save(hotel);
			
			return true;
		}
		Destination destination = destinationRepository.getOne(u.getHotel().getDestination().getId());
			
		destination.setCity(h.getDestination().getCity());
		destination.setCountry(h.getDestination().getCountry());
		destinationRepository.save(destination);
		
		Hotel hotel = hotelRepository.getOne(u.getHotel().getId());
		hotel.setName(h.getName());
		hotel.setAddress(h.getAddress());
		hotel.setPromoDescription(h.getPromoDescription());
		hotel.setDestination(destination);
		
		hotelRepository.save(hotel);
		
		return true;
	}
	
	@Transactional(readOnly=true,isolation=Isolation.READ_COMMITTED)
	public Boolean checkDate(Date date, String roomNumber, Long hotelId) {
		List<HotelRoomPrice> lista = hotelRoomPriceRepository.findBetweenDates(date, 
				hotelRoomRepository.findByRoomNumberAndHotel(roomNumber, 
						hotelRepository.findOne(hotelId)));
		System.out.println(lista);
		Boolean b = lista.isEmpty();
		System.out.println(b);
		return b;
	}
}
