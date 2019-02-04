package isa.projekat.repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Car;
import isa.projekat.model.CarType;
import isa.projekat.model.RentACarCompany;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	public Set<Car> findByCompany(RentACarCompany company);
	// public RentACarCompany findOneByEmail(String email);

	@Query("select c from Car c "+
	"where c.company=?1 and c.type=?3 and c.seats>=?4 and not exists (select cr.car from CarReservation cr where c.id=cr.car.id and cr.endDate<=?2)")
	public List<Car> findFreeCars(RentACarCompany c, Date date, CarType type,int passengers);
}
