package isa.projekat.controller;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isa.projekat.model.BranchOffice;
import isa.projekat.model.Car;
import isa.projekat.model.RentACarCompany;
import isa.projekat.model.User;
import isa.projekat.service.RentACarAdminService;
@RestController
@RequestMapping(value="/racadmin")
public class RentACarAdminController {
	
	@Autowired
	private RentACarAdminService rentACarAdminService;
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@GetMapping(value = "/i",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User whoami(HttpServletRequest request) {
		return rentACarAdminService.getAdmin(request);
	}
	

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
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@GetMapping(value = "/checkcar/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public boolean checkCar(HttpServletRequest request, @PathVariable(value = "id") String id) {
		return rentACarAdminService.checkCar(request,id);
	}
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@PostMapping(value = "/report",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String,String> getReport(HttpServletRequest request,@RequestBody Map<String,String> params) {
		return rentACarAdminService.getReport(request,params);
	}
	
	@PreAuthorize("hasRole('ROLE_RENT_A_CAR_ADMIN')")
	@PostMapping(value = "/profit",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Float getProfit(HttpServletRequest request,@RequestBody Map<String,String> params) {
		return rentACarAdminService.getProfit(request,params);
	}
	
}
