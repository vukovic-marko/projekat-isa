package isa.projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.projekat.model.HotelRoom;
import isa.projekat.model.HotelRoomPrice;

public interface HotelRoomPriceRepository extends JpaRepository<HotelRoomPrice, Long> {
	public List<HotelRoomPrice> findAllByHotelRoom(HotelRoom hotel);
}
