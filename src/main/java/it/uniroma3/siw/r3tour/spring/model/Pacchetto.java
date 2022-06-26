package it.uniroma3.siw.r3tour.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @ManyToOne
    private Referente referente;

    @ManyToOne
    private Destinazione destinazione;

}
