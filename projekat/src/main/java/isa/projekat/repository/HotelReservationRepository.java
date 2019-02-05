package isa.projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.projekat.model.HotelReservation;

public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {

}
