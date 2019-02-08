package isa.projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Destination;
import isa.projekat.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, QueryByExampleExecutor<Hotel> {
	public List<Hotel> findByDestination(Destination destination);
	@Query("select h from Hotel h,HotelRoom hr where hr.hotel=h and ?1=hr.id ")
	public Hotel findByRoom(long id);
}
