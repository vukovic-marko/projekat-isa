package isa.projekat.repository;

import isa.projekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findOneByUsername(String username);

    User findOneByEmail(String email);

    //***************************************
    boolean existsByUsername(String username);
    //***************************************
}
