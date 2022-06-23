package it.uniroma3.siw.r3tour.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
public class Credentials {

    public final static String RUOLO_ADMIN = "ADMIN";
    public final static String RUOLO_DEFAULT= "DEFAULT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private String ruolo;

    private boolean isEnabled;

    @OneToOne(cascade = {CascadeType.ALL})
    private User user;
}
