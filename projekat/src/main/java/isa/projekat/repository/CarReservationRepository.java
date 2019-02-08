package isa.projekat.repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Car;
import isa.projekat.model.CarReservation;
import isa.projekat.model.CarType;
import isa.projekat.model.RentACarCompany;
import isa.projekat.model.User;

@Repository
public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {
	
	@Query("select cr from CarReservation cr "+
			"where cr.startDate<=?2 and cr.endDate>=?1")
	public List<CarReservation> getReport(Date start,Date end);
	
	@Query("select cr from CarReservation cr,Car c where "+
			"cr.car=c and c.company=?1 and cr.endDate>=?2 and cr.endDate<=?3")
	public List<CarReservation> getReservationsByCompany(RentACarCompany c, Date dstart, Date dend);

	@Query("select cr from CarReservation cr,Car c where "+
			"cr.endDate<?2 and cr.user=?1")
	public Set<CarReservation> findUserHistory(User user, Date d);
	
	
}
