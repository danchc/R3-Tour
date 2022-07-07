package it.uniroma3.siw.r3tour.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="utente")
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String cognome;

    @NotNull
    private String telefono;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascita;

    @ManyToMany
    private List<Pacchetto> pacchetti;
}
