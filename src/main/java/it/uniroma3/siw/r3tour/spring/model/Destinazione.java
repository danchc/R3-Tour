package it.uniroma3.siw.r3tour.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Destinazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String nazione;

    @ManyToOne
    private Continente continente;

    @OneToMany(mappedBy = "destinazione", cascade = {CascadeType.ALL})
    private List<Pacchetto> pacchetti;
}
