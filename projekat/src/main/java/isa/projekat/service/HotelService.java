package isa.projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import isa.projekat.model.Hotel;
import isa.projekat.model.HotelAdditionalService;
import isa.projekat.repository.HotelRepository;

@Service
public class HotelService {
	@Autowired
	private HotelRepository hotelRepository;
	
	//dobavlja sve hotele
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	public List<Hotel> getHotels() {
		return hotelRepository.findAll();
	}
	
	//dovavlja dodatne usluge koje hotel nudi
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	public List<HotelAdditionalService> getAdditionalServices(Long id) {
		Hotel hotel = hotelRepository.findOne(id);
		
		return hotel.getAdditionalServices();
	}
}
