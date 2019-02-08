package isa.projekat.repository;

import isa.projekat.model.Car;
import isa.projekat.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long>{
    List<Destination> findByCountry(String country);

    Destination findByCountryAndCity(String country, String city);

	List<Destination> findAllByOrderByCountryAscCityAsc();

    
}
