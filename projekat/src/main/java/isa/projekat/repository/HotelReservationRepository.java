package isa.projekat.repository;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import isa.projekat.model.HotelReservation;
import isa.projekat.model.User;

public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {

	@Query("select cr from HotelReservation cr where "+
			"cr.dateOfDeparture<?2 and cr.user=?1")
	public Set<HotelReservation> findUserHistory(User user, Date d);

	
	@Query("select cr from HotelReservation cr where "+
			"cr.dateOfDeparture>=?2 and cr.user=?1")
	public Set<HotelReservation> findCurrent(User user, java.util.Date date);
	
	
	@Query("SELECT hr FROM HotelReservation hr JOIN hr.rooms r WHERE r.id = :roomId")
	Set<HotelReservation> findByRoom(@Param("roomId")Long roomId);


}
