
package isa.projekat.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import isa.projekat.model.CarReview;
import isa.projekat.model.Hotel;
import isa.projekat.model.HotelReview;
import isa.projekat.service.CarReviewService;
import isa.projekat.service.HotelReviewService;

@Controller
@RequestMapping(value = "review")
public class ReviewController {

	@Autowired
	private CarReviewService carReviewService;

	@Autowired
	private HotelReviewService hotelReviewService;

	@GetMapping(value = "/carreview/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	public CarReview getReview(@PathVariable(value = "id") String id) {

		return carReviewService.getReview(id);
	}

	@PostMapping(value = "/carreview/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	public boolean makeCarReview(@PathVariable(value = "id") String id, @RequestBody Map<String, Integer> a) {

		return carReviewService.setCarReview(id, a.get("a"));
	}

	@PostMapping(value = "/carreview/company/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	public boolean makeCompanyRev(@PathVariable(value = "id") String id, @RequestBody Map<String, Integer> a) {

		return carReviewService.setCarCompanyReview(id, a.get("a"));
	}

	@GetMapping(value = "/hotelreview/gethotelbyroom/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	public Hotel getHotelByRoom(@PathVariable(value = "id") String id) {

		return hotelReviewService.hotelByRoom(id);
	}

	@GetMapping(value = "/hotelreview/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	public HotelReview getHotelRev(@PathVariable(value = "id") String id) {

		return hotelReviewService.getReview(id);
	}
	
	
	@PostMapping(value = "/roomreview/{idres}/{idroom}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	public boolean reviewRoom(@PathVariable(value = "idres") String res,@PathVariable(value = "idroom") String room, @RequestBody Map<String, Integer> a) {

		return hotelReviewService.setRoomReview(res,room, a.get("a"));
	}
	
	@PostMapping(value = "/hotel/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	public boolean makeHotelReview(@PathVariable(value = "id") String id, @RequestBody Map<String, Integer> a) {
		return hotelReviewService.reviewHotel(id, a.get("a"));
	}
	
}
