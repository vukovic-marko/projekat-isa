package isa.projekat.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import isa.projekat.model.Car;
import isa.projekat.model.CarReservation;
import isa.projekat.model.Destination;
import isa.projekat.model.Reservation;
import isa.projekat.model.User;
import isa.projekat.repository.CarRepository;
import isa.projekat.repository.CarReservationRepository;
import isa.projekat.repository.DestinationRepository;
import isa.projekat.repository.ReservationRepository;
import isa.projekat.security.TokenUtils;

@Service
public class ReservationService {
	
	 @Autowired
	    private TokenUtils tokenUtils;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private RentACarService rentACarService;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private DestinationRepository destinationRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private CarReservationRepository carReservationRepository;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String reserve(HttpServletRequest request, Reservation reservation) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return "Unautorized";
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if (user == null)
			return "Unautorized";
		reservation.setUser(user);
		String ret="OK";
		if(reservation.getCarReservation()!=null) {
			reservation.getCarReservation().setUser(user);
			Map<String, Object> params=new HashMap<>();
			params.put("id", reservation.getCarReservation().getCar().getId().toString());
			String date=null;
			Date d=reservation.getCarReservation().getStartDate();
			date=d.getDate()+"/"+(d.getMonth()+1)+"/"+(d.getYear()+1900);
			params.put("startDate", date);

			long difference = Math.abs(reservation.getCarReservation().getEndDate().getTime()-reservation.getCarReservation().getStartDate().getTime());
			long differenceDates = difference / (24 * 60 * 60 * 1000);
			Car c=carRepository.getOne(reservation.getCarReservation().getCar().getId());
			reservation.getCarReservation().setCar(c);
			reservation.getCarReservation().setPrice(c.getPrice()* (1 + differenceDates));
			
			if(!rentACarService.checkCar(params)) {
				ret="car";
			}
			//TODO 
			//URADITI SVE STO TREBA ZA HOTEL I LET
			//AKO JE VEC REZERVISANO, kontatenirati na ret "hotel"/"flight"
			//PODESITI I ONE PARAMETRE KOJI TREBA DA SE PODESE cena, user i slicno
			//JA SAM URADIO ZA CAR RES. 
			// metode koje vam u servisu proveravaju da li je vec rezervisano
			//oznacite sa @Transactional(value=TxType.REQUIRED) (prikljucuje se postojecoj ako je ima?)
			// ili sa onim da se mora da se prikljuci postojecoj (ako je ne pozivate sa fronta ili tako nsto)
			//ako u ovoj klasi pravite nove metode koje pozivate iz ove,
			//stavite da MORA da postoji transakcija kojoj se ta prikljucuje
			
			if(ret.equals("OK")) {
				reservationRepository.save(reservation);
				
			}
			return ret;
			
		} 
		
		
		
		
		
		return null;
	}

	public Destination getDestination(String id) {
		return  destinationRepository.findOne(Long.parseLong(id));
	}

	public Set<CarReservation> getMyCarHistoty(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if (user == null)
			return null;
		Set<CarReservation> ret=new HashSet<>();
		java.util.Date date= new java.util.Date();
		java.sql.Date d=new Date(date.getTime());
		ret=carReservationRepository.findUserHistory(user,d);
		return ret;
	}
}
