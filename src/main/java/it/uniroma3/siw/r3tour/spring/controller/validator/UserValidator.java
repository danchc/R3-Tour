package it.uniroma3.siw.r3tour.spring.controller.validator;


import it.uniroma3.siw.r3tour.spring.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for User
 */
@Component
public class UserValidator implements Validator {

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;
        String nome = user.getNome().trim();
        String cognome = user.getCognome().trim();


        //se non inserisco il nome
        if (nome.isEmpty()) {
            errors.reject("user.nome.required");
        }

        //se non inserisco il cognome
        if (cognome.isEmpty()) {
            errors.reject("user.cognome.required");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

}

