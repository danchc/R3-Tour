package it.uniroma3.siw.r3tour.spring.repository;

import it.uniroma3.siw.r3tour.spring.model.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {
    public ConfirmationToken findByConfirmationToken(String confirmationToken);
}
