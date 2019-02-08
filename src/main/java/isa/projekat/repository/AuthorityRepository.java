package isa.projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	public Authority findOneByName(String name);
}
