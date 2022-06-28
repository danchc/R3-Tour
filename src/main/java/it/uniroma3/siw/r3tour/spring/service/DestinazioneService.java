package it.uniroma3.siw.r3tour.spring.service;

import it.uniroma3.siw.r3tour.spring.model.Destinazione;
import it.uniroma3.siw.r3tour.spring.repository.DestinazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DestinazioneService {

    @Autowired
    protected DestinazioneRepository destinazioneRepository;

    @Transactional
    public Destinazione inserisci(Destinazione destinazione){
        return this.destinazioneRepository.save(destinazione);
    }

    public List<Destinazione> findAllDestinazioni(){
        return (List<Destinazione>) this.destinazioneRepository.findAll();
    }

    @Transactional
    public void deleteDestinazione(Destinazione destinazione){
        this.destinazioneRepository.delete(destinazione);
    }

    public Destinazione findDestinazioneById(Long id) {
        return this.destinazioneRepository.findById(id).get();
    }
}
