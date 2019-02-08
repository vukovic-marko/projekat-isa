package isa.projekat.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import isa.projekat.model.HotelRoom;
import isa.projekat.model.HotelRoomPrice;

public interface HotelRoomPriceRepository extends JpaRepository<HotelRoomPrice, Long> {
	public List<HotelRoomPrice> findAllByHotelRoom(HotelRoom hotel);
	
    //@Query("SELECT priceTable FROM hotel_room_price priceTable WHERE priceTable.start_date <=(:date) AND priceTable.start_date >= (:date)")
	@Query(value = "SELECT p FROM HotelRoomPrice p WHERE p.startDate <= :date AND p.endDate >= :date AND p.hotelRoom = :room")
	public List<HotelRoomPrice> findBetweenDates(@Param("date") Date date, @Param("room") HotelRoom room);
	
	@Query(value = "SELECT p FROM HotelRoomPrice p WHERE p.hotelRoom = :room AND p.startDate <= :date AND p.endDate >= :date")
	public HotelRoomPrice findPriceForRoomOnDate(@Param("date") Date date, @Param("room") HotelRoom room);
}
