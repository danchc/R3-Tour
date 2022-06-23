package it.uniroma3.siw.r3tour.spring.service;

import it.uniroma3.siw.r3tour.spring.model.Credentials;
import it.uniroma3.siw.r3tour.spring.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CredentialsService {

    @Autowired
    protected CredentialsRepository credentialsRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Transactional
    public Credentials inserisci(Credentials credentials){
        credentials.setRuolo(Credentials.RUOLO_DEFAULT);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }

    public boolean alreadyExistsUsername(String username){
        return credentialsRepository.existsByUsername(username);
    }

    public Credentials findByEmail(String email) {
        return this.credentialsRepository.findByEmail(email).get();
    }

    public Credentials findCredentialsByUsername(String username){
        return this.credentialsRepository.findByUsername(username).get();
    }
}
