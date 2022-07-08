package it.uniroma3.siw.r3tour.spring.repository;

import it.uniroma3.siw.r3tour.spring.model.Pacchetto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PacchettoRepository extends CrudRepository<Pacchetto, Long> {

    List<Pacchetto> findAllByDestinazione_Nome(String nome);
}
