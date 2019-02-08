package isa.projekat.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isa.projekat.model.Car;
import isa.projekat.model.Destination;
import isa.projekat.model.RentACarCompany;
import isa.projekat.service.RentACarService;
@RestController
@RequestMapping(value="/rentacar")
public class RentACarController {
	
	@Autowired
	private RentACarService rentACarService;
	
	//svako moze da pristupa
	@GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<RentACarCompany> getAll() {
		return rentACarService.getAll();
	}
	
	@GetMapping(value = "/{id}/destinations",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Set<Destination> destinationsOfService( @PathVariable(value = "id") String id) {
		return rentACarService.getDestinations(id);
	}
	
	@GetMapping(value = "/destinations",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Destination> allDestinations( ) {
		return rentACarService.getAllDestinations();
	}
	
	//vraca automobile koji su dostupni
	@PostMapping(value = "/freecars",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Car> getcars( @RequestBody Map<String,String> params) {
		return rentACarService.getFreeCars(params);
	}
	
	@PostMapping(value = "/findcompanies",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Set<RentACarCompany> finCompanies(@RequestBody Map<String,String> params) {
		return rentACarService.getCompanies(params);
	}
	
	
	@PostMapping(value = "/checkiffree",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public boolean chekcIfFree(@RequestBody Map<String,Object> params) {
		return rentACarService.checkCar(params);
	}
	
	
	
	@PostMapping(value = "/getcar",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Car getCar( @RequestBody Map<String,String> params) {
		return rentACarService.getCar(params);
	}
	
	
	@GetMapping(value = "/average/{id}")
	@ResponseBody
	public Double getAverageReviewForCar( @PathVariable(value="id")String id) {
		return rentACarService.getAverage(id);
	}
	@GetMapping(value = "/average/company/{id}")
	@ResponseBody
	public Double getCompanyAvg( @PathVariable(value="id")String id) {
		return rentACarService.getCompanyAvg(id);
	}
}
