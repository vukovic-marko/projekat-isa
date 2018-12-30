package isa.projekat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isa.projekat.model.User;
import isa.projekat.service.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping(value = "/register",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Boolean> register(@RequestBody User u){
		boolean reg=userService.register(u);
		return new ResponseEntity<Boolean>(reg,HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/login",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean login(User u){
		return false;	
	}
	@GetMapping(value = "/checkemail")
	public Boolean checkEmail(@RequestParam(name="email")String email) {
		return userService.checkEmail(email);
	}
	@GetMapping(value = "/checkusername")
	public Boolean checkUsername(@RequestParam(name="username") String username) {
		return userService.checkUsername(username);
	}

	@RequestMapping(value = "/activate/{id}",
			method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Boolean> activate(@PathVariable String id){
		boolean reg=userService.activate(Long.parseLong(id));
		
		return new ResponseEntity<Boolean>(reg,HttpStatus.OK);	
	}
	
	
}
