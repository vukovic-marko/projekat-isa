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
	public boolean chekcIfFree(@RequestBody Map<String,String> params) {
		return rentACarService.checkCar(params);
	}
	/*
	//@GetMapping(value = "/cars/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public boolean delete(HttpServletRequest request, @PathVariable(value = "id") String id) {
		return rentACarAdminService.delete(request,id);
	}
	*/
/*
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@PostMapping(value = "/branch",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Long addBranch(HttpServletRequest request,@RequestBody BranchOffice bo) {
		return rentACarAdminService.addBranch(request,bo);
	}
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@GetMapping(value = "/company",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public RentACarCompany company(HttpServletRequest request) {
		return rentACarAdminService.company(request);
	}
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@DeleteMapping(value = "/branch/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public boolean delete(HttpServletRequest request, @PathVariable(value = "id") String id) {
		return rentACarAdminService.delete(request,id);
	}
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@PutMapping(value = "/branch",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public boolean editbranch(HttpServletRequest request, @RequestBody BranchOffice bo) {
		return rentACarAdminService.editBranch(request,bo);
	}
	
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@PutMapping( consumes = MediaType.APPLICATION_JSON_VALUE, value = "/edit",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public boolean edit(HttpServletRequest request,@RequestBody User u) {
		return rentACarAdminService.edit(request, u);
	}
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@PutMapping( consumes = MediaType.APPLICATION_JSON_VALUE, value = "/editcompany",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public boolean editCompany(HttpServletRequest request,@RequestBody RentACarCompany c) {
		return rentACarAdminService.editCompany(request, c);
	}
		
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@GetMapping(value = "/cars",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Set<Car> getCars(HttpServletRequest request) {
		return rentACarAdminService.getCars(request);
	}
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@PostMapping(value = "/car",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Long addCar(HttpServletRequest request,@RequestBody Car c) {
		return rentACarAdminService.addCar(request,c);
	}
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@PutMapping(value = "/car",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public boolean editCar(HttpServletRequest request,@RequestBody Car c) {
		return rentACarAdminService.editCar(request,c);
	}
	

	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@DeleteMapping(value = "/car/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public boolean deleteCar(HttpServletRequest request, @PathVariable(value = "id") String id) {
		return rentACarAdminService.deleteCar(request,id);
	}
	*/
}
