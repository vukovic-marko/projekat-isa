package isa.projekat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.projekat.model.CarReview;
import isa.projekat.model.Hotel;
import isa.projekat.model.HotelReservation;
import isa.projekat.model.HotelReview;
import isa.projekat.model.RoomReview;
import isa.projekat.repository.HotelRepository;
import isa.projekat.repository.HotelReservationRepository;
import isa.projekat.repository.HotelReviewRepository;
import isa.projekat.repository.HotelRoomRepository;

@Service
public class HotelReviewService {

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private HotelReviewRepository hotelReviewRepository;

	@Autowired
	private HotelRoomRepository hotelRoomRepository;

	

	@Autowired
	private HotelReservationRepository hotelReservationRepository;

	
	/*public CarReview getReview(String id) {
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
*/
	public Hotel hotelByRoom(String id) {
		return hotelRepository.findByRoom(Long.parseLong(id));
		 
	}


	public HotelReview getReview(String id) {
		return hotelReviewRepository.findOne(Long.parseLong(id));
	}


	public boolean setRoomReview(String res, String room, Integer integer) {
		HotelReservation c=hotelReservationRepository.findOne(Long.parseLong(res));
		if(c==null) 
			return false;
		HotelReview r=hotelReviewRepository.findByReservationId(c.getId());
		if(r==null)
			r=new HotelReview();
		r.setHotelReservation(c);
	
		List<RoomReview> rr=r.getRoomReviews();
		if(rr==null)
			rr=new ArrayList<>();
		RoomReview rom=new RoomReview();
		rom.setReview(integer);
		rom.setHotelRoom(hotelRoomRepository.findOne(Long.parseLong(room)));
		rr.add(rom);
		hotelReviewRepository.save(r);
		return true;
		
	}


	public boolean reviewHotel(String id, Integer integer) {
		HotelReservation c=hotelReservationRepository.findOne(Long.parseLong(id));
		if(c==null) 
			return false;
		HotelReview r=hotelReviewRepository.findByReservationId(c.getId());
		if(r==null)
			r=new HotelReview();
		r.setHotelReservation(c);
		r.setHotelReview(integer);
		hotelReviewRepository.save(r);
		return true;
		
	}
	
	public Double getAverage(String id) {
		List<HotelReview> rev= hotelReviewRepository.getByHotelId(Long.parseLong(id));
		Double sum=0.0;
		int i=0;
		for(HotelReview r:rev)
			if(r.getHotelReview()!=null) {
				i++;
				sum+=r.getHotelReview();
			}
		if(i==0)
			i=1;
		return sum/i;
	}
	
	public Double getAverageRoom(String id) {
		List<HotelReview> rev= hotelReviewRepository.getByRoomId(Long.parseLong(id));
		System.out.println(rev);
		Double sum=0.0;
		int i=0;
		for(HotelReview r:rev)
			if(r.getRoomReviews()!=null) {
				i++;
				//sum+=r.getHotelReview();
				for (RoomReview rr : r.getRoomReviews()) {
					System.out.println(rr.getHotelRoom().getId() + " == " + Long.parseLong(id));
					if (rr.getHotelRoom().getId() == Long.parseLong(id)) {
						sum += rr.getReview();
						break;
					}
				}
			}
		if(i==0)
			i=1;
		return sum/i;
	}
}
