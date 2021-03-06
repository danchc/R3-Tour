package it.uniroma3.siw.r3tour.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pacchetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String descrizione;

    @NotNull
    private Integer prezzo;

    /* periodo */
    @NotNull
    private Integer numeroGiorni;

    @NotNull
    private Integer numeroNotti;
    /* periodo */

    private String photo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Referente referente;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Destinazione destinazione;

    @ManyToMany
    @JoinColumn(name="pacchetto_id")
    private List<User> prenotazioni;

}
