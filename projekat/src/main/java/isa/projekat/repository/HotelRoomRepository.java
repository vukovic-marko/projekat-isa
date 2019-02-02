package isa.projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Hotel;
import isa.projekat.model.HotelRoom;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {
	public HotelRoom findByRoomNumberAndHotel(String roomNumber, Hotel hotel);
}
