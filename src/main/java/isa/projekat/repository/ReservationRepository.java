package isa.projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.projekat.model.CarReservation;
import isa.projekat.model.HotelReservation;
import isa.projekat.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	public Reservation findByCarReservation(CarReservation cr);
	public Reservation findByHotelReservation(HotelReservation cr);
}
