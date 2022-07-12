package it.uniroma3.siw.r3tour.spring.controller.validator;

import it.uniroma3.siw.r3tour.spring.model.Pacchetto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PacchettoValidator implements Validator {

    private final int PREZZO_MIN = 1;

    private final int NUMERO_NOTTI_MIN = 1;

    private final int NUMERO_GIORNI_MIN = 1;

    @Override
    public boolean supports(Class<?> clazz) {
        return Pacchetto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pacchetto pacchetto = (Pacchetto) target;
        String nome = pacchetto.getNome().trim();
        String descrizione = pacchetto.getDescrizione().trim();
        Integer prezzo = pacchetto.getPrezzo();
        Integer numeroNotti = pacchetto.getNumeroNotti();
        Integer numeroGiorni = pacchetto.getNumeroGiorni();

        /* controllo nome */
        if(nome.isEmpty()){
            errors.reject("pacchetto.nome.required");
        }

        /* controllo cognome */
        if(descrizione.isEmpty()){
            errors.reject("pacchetto.descrizione.required");
        }

        /* controllo nome */
        if(prezzo < PREZZO_MIN){
            errors.reject("pacchetto.prezzo.min");
        }

        /* controllo numero giorni */
        if(numeroGiorni < NUMERO_GIORNI_MIN) {
            errors.reject("pacchetto.numerogiorni.min");
        }

        /* controllo numero notti */
        if(numeroNotti < NUMERO_NOTTI_MIN) {
            errors.reject("pacchetto.numeronotti.min");
        }


    }
}
