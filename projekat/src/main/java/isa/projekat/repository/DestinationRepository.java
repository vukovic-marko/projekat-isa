package isa.projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Destination;
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long>{
	public List<Destination> findByCountry(String country);
	public Destination findByCountryAndCity(String country, String city);
}
