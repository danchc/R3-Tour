package it.uniroma3.siw.r3tour.spring.service;

import it.uniroma3.siw.r3tour.spring.model.Destinazione;
import it.uniroma3.siw.r3tour.spring.model.Pacchetto;
import it.uniroma3.siw.r3tour.spring.repository.DestinazioneRepository;
import it.uniroma3.siw.r3tour.spring.repository.PacchettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PacchettoService {

    @Autowired
    protected PacchettoRepository pacchettoRepository;

    /**
     * Questo metodo viene utilizzato per salvare un nuovo pacchetto all'interno del database.
     * @param pacchetto
     * @return il pacchetto salvato.
     */
    @Transactional
    public Pacchetto inserisci(Pacchetto pacchetto){
        return this.pacchettoRepository.save(pacchetto);
    }

    /**
     * Questo metodo viene utilizzato per cercare tutti i pacchetti all'interno del database.
     * @return la lista dei pacchetti
     */
    public List<Pacchetto> findAllPacchetti(){
        return (List<Pacchetto>) this.pacchettoRepository.findAll();
    }

    /**
     * Questo metodo viene utilizzato per trovare un pacchetto in base all'id passato come parametro.
     * @param id
     * @return il pacchetto trovato tramite id
     */
    public Pacchetto findPacchettoById(Long id) {
        return this.pacchettoRepository.findById(id).get();
    }

    /**
     * Questo metodo viene utilizzato per gestire l'eliminazione di un pacchetto dal database.
     * @param pacchetto
     * @return true o false
     */
    @Transactional
    public boolean deletePacchetto(Pacchetto pacchetto) {
        try {
            this.pacchettoRepository.delete(pacchetto);
            return true;
        } catch(Exception e) {
            return false;
        }
    }


    public List<Pacchetto> findPacchettiByDestinazione(String nomeDestinazione){
        return this.pacchettoRepository.findAllByDestinazione_Nome(nomeDestinazione);
    }
}
