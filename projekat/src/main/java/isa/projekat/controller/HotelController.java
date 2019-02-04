package isa.projekat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import isa.projekat.model.Hotel;
import isa.projekat.service.HotelService;

@RestController
@RequestMapping(value="/hotel")
public class HotelController {
	@Autowired
	private HotelService hotelService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Hotel> getHotels() {
		return hotelService.getHotels(); 
	}
}