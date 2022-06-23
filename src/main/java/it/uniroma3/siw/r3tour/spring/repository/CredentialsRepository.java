package it.uniroma3.siw.r3tour.spring.repository;

import it.uniroma3.siw.r3tour.spring.model.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

    boolean existsByUsername(String username);

    Optional<Credentials> findByEmail(String email);

    Optional<Credentials> findByUsername(String username);
}
