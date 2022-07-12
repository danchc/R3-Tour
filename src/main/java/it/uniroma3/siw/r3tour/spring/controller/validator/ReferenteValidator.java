package it.uniroma3.siw.r3tour.spring.controller.validator;

import it.uniroma3.siw.r3tour.spring.model.Referente;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReferenteValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Referente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Referente referente = (Referente) target;
        String nome = referente.getNome().trim();
        String cognome = referente.getCognome().trim();
        String email = referente.getEmail().trim();
        String telefono = referente.getTelefono().trim();

        /* controllo nome */
        if(nome.isEmpty()){
            errors.reject("referente.nome.required");
        }

        /* controllo cognome */
        if(cognome.isEmpty()) {
            errors.reject("referente.cognome.required");
        }

        /* controllo email */
        if(email.isEmpty()) {
            errors.reject("referente.email.required");
        }

        /* controllo telefono */
        if(telefono.isEmpty()){
            errors.reject("referente.telefono.required");
        }
    }
}
