package it.uniroma3.siw.r3tour.spring.service;

import it.uniroma3.siw.r3tour.spring.model.Referente;
import it.uniroma3.siw.r3tour.spring.repository.ReferenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReferenteService {

    @Autowired
    protected ReferenteRepository referenteRepository;

    /**
     * Il metodo viene utilizzato per inserire un nuovo referente all'interno del database.
     * @param referente
     * @return il referente
     */
    @Transactional
    public Referente inserisci(Referente referente){
        return this.referenteRepository.save(referente);
    }

    /**
     * Il metodo viene utilizzato per ottenere la lista dei referenti presenti all'interno del database.
     * @return la lista
     */
    public List<Referente> findAllReferenti() {
        return (List<Referente>) this.referenteRepository.findAll();
    }


    /**
     * Il metodo viene utilizzato per eliminare un determinato referente dal database.
     * @param referente
     */
    @Transactional
    public void deleteReferente(Referente referente){
        this.referenteRepository.delete(referente);
    }

    /**
     * Il metodo viene utilizzato per ottenere un determinato referente in base all'id passato
     * come parametro.
     * @param id
     * @return il referente cercato
     */
    public Referente findReferenteById(Long id) {
        return this.referenteRepository.findById(id).get();
    }
}
