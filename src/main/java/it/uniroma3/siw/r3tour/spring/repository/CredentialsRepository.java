package it.uniroma3.siw.r3tour.spring.repository;

import it.uniroma3.siw.r3tour.spring.model.Credentials;
import org.springframework.data.repository.CrudRepository;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

    boolean existsByUsername(String username);
}
