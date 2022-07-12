package it.uniroma3.siw.r3tour.spring.service;

import it.uniroma3.siw.r3tour.spring.model.ConfirmationToken;
import it.uniroma3.siw.r3tour.spring.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ConfirmationTokenService {

    @Autowired
    protected ConfirmationTokenRepository confirmationTokenRepository;

    /**
     * Il metodo salva all'interno del database un determinato token creato in maniera randomica.
     * @param confirmationToken
     * @return il token creato
     */
    @Transactional
    public ConfirmationToken save(ConfirmationToken confirmationToken){
        return this.confirmationTokenRepository.save(confirmationToken);
    }
}
