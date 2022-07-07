package it.uniroma3.siw.r3tour.spring.service;

import it.uniroma3.siw.r3tour.spring.model.Credentials;
import it.uniroma3.siw.r3tour.spring.model.Destinazione;
import it.uniroma3.siw.r3tour.spring.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CredentialsService {

    @Autowired
    protected CredentialsRepository credentialsRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    /**
     * Il metodo gestisce l'inserimento di un nuovo utente nel database.
     * @param credentials
     * @return l'utente appena registrato
     */
    @Transactional
    public Credentials inserisci(Credentials credentials){
        credentials.setRuolo(Credentials.RUOLO_DEFAULT);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }

    /**
     * Il metodo gestisce l'aggiornamento dell'entità Credentials.
     * @param credentials
     * @return l'entità Credentials aggiornata
     */
    @Transactional
    public Credentials update(Credentials credentials){
        return this.credentialsRepository.save(credentials);
    }

    /**
     * Il metodo ritorna true se l'utente con un determinato username esiste nel database, false altrimenti
     * @param username
     * @return true o false
     */
    public boolean alreadyExistsUsername(String username){
        return credentialsRepository.existsByUsername(username);
    }

    /**
     * Il metodo serve per cercare un determinato utente in base all'email.
     * @param email
     * @return l'utente trovato
     */
    public Credentials findByEmail(String email) {
        return this.credentialsRepository.findByEmail(email).get();
    }

    /**
     * Il metodo serve per cercare un determinato utente in base all'username.
     * @param username
     * @return l'utente trovato
     */
    public Credentials findCredentialsByUsername(String username){
        return this.credentialsRepository.findByUsername(username).get();
    }

    /**
     * Il metodo serve per cercare tutti gli utenti all'interno del database.
     * @return la lista degli utenti nel database
     */
    public List<Credentials> findAllUtentiRegistrati(){
        return (List<Credentials>) this.credentialsRepository.findAll();
    }

    /**
     * Il metodo serve per cercare le credenziali di un utente in base all'id.
     * @param id
     * @return le credenziali dell'utente
     */
    public Credentials findCredentialsById(Long id) {
        return this.credentialsRepository.findById(id).get();
    }

    /**
     * Il metodo viene utilizzato per eliminare un determinato utente dal database.
     * @param credentials
     * @return
     */
    public boolean deleteCredentials(Credentials credentials){
        try {
            this.credentialsRepository.delete(credentials);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Il metodo trova i dati dell'utente loggato.
     * @return l'utente loggato.
     */
    public Credentials getCredentialsAuthenticated() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.findCredentialsByUsername(userDetails.getUsername());
        return credentials;
    }
}
