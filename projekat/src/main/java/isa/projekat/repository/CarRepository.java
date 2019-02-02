package isa.projekat.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Car;
import isa.projekat.model.RentACarCompany;
@Repository
public interface CarRepository extends JpaRepository<Car, Long>{
	public Set<Car> findByCompany(RentACarCompany company);
	//public RentACarCompany findOneByEmail(String email);
}
