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

    /**
     * Il metodo viene utilizzato per salvare all'interno del database una nuova destinazione.
     * @param destinazione
     * @return la destinazione appena salvata
     */
    @Transactional
    public Destinazione inserisci(Destinazione destinazione){
        return this.destinazioneRepository.save(destinazione);
    }


    /**
     * Il metodo viene utilizzato per ottenere una lista di tutte le destinazioni presenti nel database.
     * @return la lista
     */
    public List<Destinazione> findAllDestinazioni(){
        return (List<Destinazione>) this.destinazioneRepository.findAll();
    }

    /**
     * Il metodo viene utilizzato per eliminare dal database una determinata destinazione esistente.
     * @param destinazione
     */
    @Transactional
    public void deleteDestinazione(Destinazione destinazione){
        this.destinazioneRepository.delete(destinazione);
    }

    /**
     * Il metodo viene utilizzato per ottenere una destinazione in base all'id passato come parametro.
     * @param id
     * @return la destinazione cercata
     */
    public Destinazione findDestinazioneById(Long id) {
        return this.destinazioneRepository.findById(id).get();
    }
}
