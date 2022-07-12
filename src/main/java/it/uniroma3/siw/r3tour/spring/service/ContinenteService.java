package it.uniroma3.siw.r3tour.spring.service;

import it.uniroma3.siw.r3tour.spring.model.Continente;
import it.uniroma3.siw.r3tour.spring.repository.ContinenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContinenteService {

    @Autowired
    protected ContinenteRepository continenteRepository;

    /**
     * Il metodo viene utilizzato per ottenere una lista di tutti i continenti presenti nel database.
     * @return la lista
     */
    public List<Continente> findAllContinenti() {
        return (List<Continente>) this.continenteRepository.findAll();
    }
}
