package isa.projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isa.projekat.model.CarReview;

@Repository
public interface CarReviewRepository extends JpaRepository<CarReview, Long> {

	@Query("select cr from CarReview cr where cr.carReservation.id=?1")
	CarReview findByReservationId(Long l);

	@Query("select cr from CarReview cr, CarReservation res"+
	" where cr.carReservation.car.id=?1")
	List<CarReview> getByCarId(Long id);

	@Query("select cr from CarReview cr, CarReservation res"+
	" where cr.carReservation.car.company.id=?1")
	List<CarReview> getByCompanyId(long parseLong);
	
	
}
