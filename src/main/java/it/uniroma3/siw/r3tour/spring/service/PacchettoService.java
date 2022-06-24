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

    @Transactional
    public Pacchetto inserisci(Pacchetto pacchetto){
        return this.pacchettoRepository.save(pacchetto);
    }

    public List<Pacchetto> findAllPacchetti(){
        return (List<Pacchetto>) this.pacchettoRepository.findAll();
    }
}
