package isa.projekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import isa.projekat.model.Destination;
import isa.projekat.model.Hotel;
import isa.projekat.model.User;
import isa.projekat.repository.DestinationRepository;
import isa.projekat.repository.HotelRepository;
import isa.projekat.repository.UserRepository;

@Service
public class HotelAdminService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private DestinationRepository destinationRepository;
	
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED)
	public Boolean editHotel(User u, Hotel h) {
		if (u.getHotel() == null) {
			Destination destination = new Destination();
			destination.setCity(h.getDestination().getCity());
			destination.setCountry(h.getDestination().getCountry());
			destinationRepository.save(destination);
			
			Hotel hotel = new Hotel();
			hotel.setName(h.getName());
			hotel.setAddress(h.getAddress());
			hotel.setPromoDescription(h.getPromoDescription());
			hotel.setDestination(destination);
			hotel.setAdmin(u);
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
}
