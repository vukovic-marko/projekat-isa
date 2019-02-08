
package isa.projekat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import isa.projekat.model.Destination;
import isa.projekat.model.Reservation;
import isa.projekat.service.ReservationService;

@Controller
@RequestMapping(value = "/reservate")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@PostMapping(value = "/add")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<String> method(HttpServletRequest request, @RequestBody Reservation reservation) {
		String ret = reservationService.reserve(request, reservation);
		if (ret.equals("OK"))
			return new ResponseEntity<>(ret, HttpStatus.OK);
		else
			return new ResponseEntity<>(ret, HttpStatus.FORBIDDEN);
	}

	@GetMapping(value = "/dest/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	public Destination getDest(@PathVariable(value = "id") String id) {
		Destination d = reservationService.getDestination(id);
		return d;
	}

}
