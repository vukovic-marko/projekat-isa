package isa.projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.projekat.model.Authority;
import isa.projekat.model.BranchOffice;

@Repository
public interface BranchesRepository extends JpaRepository<BranchOffice, Long>{
}
