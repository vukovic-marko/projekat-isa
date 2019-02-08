package isa.projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isa.projekat.model.CarReview;
import isa.projekat.model.HotelReview;

@Repository
public interface HotelReviewRepository extends JpaRepository<HotelReview, Long> {

	@Query("select cr from HotelReview cr where cr.hotelReservation.id=?1")
	HotelReview findByReservationId(Long l);
/*
	@Query("select cr from CarReview cr, CarReservation res"+
	" where cr.carReservation.car.id=?1")
	List<CarReview> getByCarId(Long id);

	@Query("select cr from CarReview cr, CarReservation res"+
	" where cr.carReservation.car.company.id=?1")
	List<CarReview> getByCompanyId(long parseLong);
	*/	
	
	@Query("select hr from HotelReview hr, HotelReservation res JOIN res.rooms r"+
			" where r.hotel.id=?1")
	List<HotelReview> getByHotelId(long parseLong);
	
	@Query("select hr from HotelReview hr, HotelReservation res JOIN res.rooms r"+
			" where r.id=?1")
	List<HotelReview> getByRoomId(long parseLong);
}
