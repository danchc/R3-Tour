package it.uniroma3.siw.r3tour.spring.controller.validator;

import it.uniroma3.siw.r3tour.spring.model.Credentials;
import it.uniroma3.siw.r3tour.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CredentialsValidator implements Validator {

    @Autowired
    protected CredentialsService credentialsService;

    /* regole per lunghezza min/max */
    final Integer MAX_USERNAME_LENGTH = 15;
    final Integer MIN_USERNAME_LENGTH = 3;
    final Integer MIN_PSW_LENGTH = 8;

    @Override
    public void validate(Object target, Errors errors) {

        Credentials credentials = (Credentials) target;
        /* variabili immesse dall'utente senza spazi bianchi */
        String username = credentials.getUsername().trim();
        String password = credentials.getPassword().trim();
        String email = credentials.getEmail().trim();

        /* inizio a verificare la correttezza dei dati */

        /* controlliamo se è duplicato */
        if(credentialsService.alreadyExistsUsername(username)){
            errors.reject("credentials.username.duplicato");
        }

        /* se non è duplicato allora controllo lo username se ha qualche errore */
        if(username.isEmpty()){
            errors.reject("credentials.username.required");
        } else if(username.length() > MAX_USERNAME_LENGTH || username.length() < MIN_USERNAME_LENGTH){
            errors.reject("credentials.username.size");
        }

        /* e infine la password */
        if(password.isEmpty()){
            errors.reject("credentials.psw.required");
        } else if(password.length() < MIN_PSW_LENGTH){
            errors.reject("credentials.psw.size");
        }

        //se non inserisco l'email
        if (email.isEmpty()) {
            errors.reject("user.email.required");
        }

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Credentials.class.equals(clazz);
    }
}