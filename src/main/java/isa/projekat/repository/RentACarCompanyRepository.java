package isa.projekat.repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Car;
import isa.projekat.model.Destination;
import isa.projekat.model.RentACarCompany;
import isa.projekat.model.User;
@Repository
public interface RentACarCompanyRepository extends JpaRepository<RentACarCompany, Long>{
	public RentACarCompany findOneByAdmin(User admin);
	
	@Query("select distinct  rac from RentACarCompany rac, Car c "+
			"where c.company=rac and (?2=null or rac.location=?2) and (?1=null or rac.name like ?1)"+
			" and c not in(select res.car from CarReservation res where endDate>=?3)")
	public Set<RentACarCompany> findByNameLike(String name,Destination location,Date date);
}
