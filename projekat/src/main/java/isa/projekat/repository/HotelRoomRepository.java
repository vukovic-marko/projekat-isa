package isa.projekat.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Hotel;
import isa.projekat.model.HotelRoom;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {
	public HotelRoom findByRoomNumberAndHotel(String roomNumber, Hotel hotel);

	@Query(value = "SELECT r FROM HotelRoom r JOIN r.roomReservations res WHERE res.dateOfArrival <= :date AND res.dateOfDeparture >= :date")
	public List<HotelRoom> find(@Param("date") Date date);
	
	@Query(value = "SELECT r FROM HotelRoom r "
			 	 + "JOIN r.hotel h LEFT JOIN r.roomReservations res "
				 + "WHERE h.id = :hotelId "
				 + "AND ((res.dateOfArrival <= :dateOfArrival AND res.dateOfDeparture >= :dateOfArrival) "
				 + "OR (res.dateOfArrival <= :dateOfDeparture AND res.dateOfDeparture >= :dateOfDeparture) "
				 + "OR (res.dateOfArrival >= :dateOfArrival AND res.dateOfDeparture <= :dateOfDeparture) "
				 + "OR r.size < :size)")
	public List<HotelRoom> findUnavailableRooms(@Param("dateOfArrival") Date dateOfArrival, 
			@Param("dateOfDeparture") Date dateOfDeparture, 
			@Param("size") Long size, 
			@Param("hotelId") Long hotelId);
}
