package isa.projekat.repository;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Car;
import isa.projekat.model.RentACarCompany;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	public Set<Car> findByCompany(RentACarCompany company);
	// public RentACarCompany findOneByEmail(String email);

	@Query("select c from Car c "+
	"where c.company=?1 and not exists (select cr.car from CarReservation cr where cr.endDate<=?2)")
	public Set<Car> findFreeCars(RentACarCompany c, Date date);
}
