package isa.projekat.repository;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import isa.projekat.model.HotelReservation;
import isa.projekat.model.User;

public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {

	@Query("select cr from HotelReservation cr where "+
			"cr.dateOfDeparture<?2 and cr.user=?1")
	Set<HotelReservation> findUserHistory(User user, Date d);

}
