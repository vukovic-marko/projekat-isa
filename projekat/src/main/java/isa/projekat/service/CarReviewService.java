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
import isa.projekat.model.CarReview;
import isa.projekat.model.Destination;
import isa.projekat.model.Reservation;
import isa.projekat.model.User;
import isa.projekat.repository.CarRepository;
import isa.projekat.repository.CarReservationRepository;
import isa.projekat.repository.CarReviewRepository;
import isa.projekat.repository.DestinationRepository;
import isa.projekat.repository.RentACarCompanyRepository;
import isa.projekat.repository.ReservationRepository;
import isa.projekat.security.TokenUtils;

@Service
public class CarReviewService {
	
	@Autowired
	private CarReviewRepository carReviewRepository;
	@Autowired
	private CarReservationRepository carReservationRepository;

	
	public CarReview getReview(String id) {
		CarReview c=carReviewRepository.findByReservationId(Long.parseLong(id));
		
		return c;
	}

	public boolean setCarReview( String id, int a) {
		CarReview c=carReviewRepository.findByReservationId(Long.parseLong(id));
		if(c==null) {
			c=new CarReview();
			c.setCarReservation(carReservationRepository.findOne(Long.parseLong(id)));
		}
		c.setCarReview(a);
		carReviewRepository.save(c);
		return true;
	}

	public boolean setCarCompanyReview(String id, Integer a) {
		CarReview c=carReviewRepository.findByReservationId(Long.parseLong(id));
		if(c==null) {
			c=new CarReview();
			c.setCarReservation(carReservationRepository.findOne(Long.parseLong(id)));
		}
		c.setCompanyReview(a);
		carReviewRepository.save(c);
		return true;
	}
}
