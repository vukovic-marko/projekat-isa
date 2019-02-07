package isa.projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Destination;
import isa.projekat.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, QueryByExampleExecutor<Hotel> {
	public List<Hotel> findByDestination(Destination destination);
}
