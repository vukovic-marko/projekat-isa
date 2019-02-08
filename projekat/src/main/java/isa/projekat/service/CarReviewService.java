package isa.projekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.projekat.model.CarReview;
import isa.projekat.repository.CarReservationRepository;
import isa.projekat.repository.CarReviewRepository;

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
