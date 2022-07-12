package it.uniroma3.siw.r3tour.spring.controller.validator;

import it.uniroma3.siw.r3tour.spring.model.Destinazione;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DestinazioneValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Destinazione.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Destinazione destinazione = (Destinazione) target;
        String nome = destinazione.getNome().trim();
        String nazione = destinazione.getNazione().trim();

        /* controllo nome */
        if(nome.isEmpty()) {
            errors.reject("destinazione.nome.required");
        }

        /* controllo nazione */
        if(nazione.isEmpty()){
            errors.reject("destinazione.nazione.required");
        }
    }
}
