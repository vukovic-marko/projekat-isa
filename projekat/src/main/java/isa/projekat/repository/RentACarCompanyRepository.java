package isa.projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.projekat.model.RentACarCompany;
import isa.projekat.model.User;
@Repository
public interface RentACarCompanyRepository extends JpaRepository<RentACarCompany, Long>{
	public RentACarCompany findOneByAdmin(User admin);
	//public RentACarCompany findOneByEmail(String email);
}
