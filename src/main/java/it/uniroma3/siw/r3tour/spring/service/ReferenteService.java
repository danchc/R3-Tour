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

    @Transactional
    public Referente inserisci(Referente referente){
        return this.referenteRepository.save(referente);
    }

    public List<Referente> findAllReferenti() {
        return (List<Referente>) this.referenteRepository.findAll();
    }
}
