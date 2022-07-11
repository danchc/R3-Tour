package it.uniroma3.siw.r3tour.spring.service;

import it.uniroma3.siw.r3tour.oauth.Provider;
import it.uniroma3.siw.r3tour.spring.model.Credentials;
import it.uniroma3.siw.r3tour.oauth.CustomOAuth2User;
import it.uniroma3.siw.r3tour.spring.model.User;
import it.uniroma3.siw.r3tour.spring.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

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
        credentials.setProvider(Provider.LOCAL);
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
        credentials.setProvider(Provider.LOCAL);
        credentials.setRuolo(Credentials.RUOLO_DEFAULT);
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

    /**
     * Questo metodo gestisce le operazioni dopo l'accesso di un utente attraverso
     * l'utilizzo di OAuth2.
     * @param username
     * @param oAuth2User
     */
    public void processOAuthPostLogin(String username, CustomOAuth2User oAuth2User) {
        Credentials newCredentials = new Credentials();
        User newUser = new User();
        if (!this.credentialsRepository.existsByUsername(username)) {
            if(oAuth2User.getAttributes().keySet().contains("given_name")) {
                newUser.setNome(oAuth2User.getAttribute("given_name"));
                newUser.setCognome(oAuth2User.getAttribute("family_name"));

                newCredentials.setEmail(oAuth2User.getAttribute("email"));
                newCredentials.setUser(newUser);
                newCredentials.setUsername(oAuth2User.getUsername());
                newCredentials.setPassword(UUID.randomUUID().toString());
                newCredentials.setProvider(Provider.GOOGLE);
                newCredentials.setEnabled(true);
                newCredentials.setRuolo(Credentials.RUOLO_DEFAULT);
                this.update(newCredentials);
            } else {
                String nomeCompleto = oAuth2User.getAttribute("name");
                String[] parti = nomeCompleto.split(" ");
                newUser.setNome(parti[0]);
                newUser.setCognome(parti[1]);

                newCredentials.setUser(newUser);
                newCredentials.setEmail(oAuth2User.getAttribute("email"));
                newCredentials.setUsername(oAuth2User.getUsername());
                newCredentials.setPassword(UUID.randomUUID().toString());
                newCredentials.setProvider(Provider.FACEBOOK);
                newCredentials.setEnabled(true);
                newCredentials.setRuolo(Credentials.RUOLO_DEFAULT);
                this.update(newCredentials);
            }

        }

    }
}
