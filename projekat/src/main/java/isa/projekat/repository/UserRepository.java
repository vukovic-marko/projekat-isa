package isa.projekat.repository;

import isa.projekat.model.Authority;
import isa.projekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUsername(String username);

    User findOneByEmail(String email);

    //***************************************
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Long countDistinctByEmail(String email);

    // Filter registered users by firstName, lastName, email and of same authority
    List<User> findByFirstNameContainingAndLastNameContainingAndEmailContainingAndActivatedIsTrueAndAuthoritiesIsInAndIdIsNotInAllIgnoreCase(
            String firstName, String lastName, String email,
            Collection<? extends GrantedAuthority> userAuthorities,
            List<Long> friendsIdList);
    //***************************************
}
