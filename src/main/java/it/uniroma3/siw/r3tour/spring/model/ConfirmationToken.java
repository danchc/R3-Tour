package it.uniroma3.siw.r3tour.spring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = Credentials.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "credentials_id")
    private Credentials credentials;

    public ConfirmationToken(Credentials credentials) {
        this.credentials = credentials;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

}