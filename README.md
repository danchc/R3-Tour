
# ✈️ R3 TOUR ✈️
![logo](https://i.postimg.cc/DZPRmFLW/logo.png)

Progetto Personale Sistemi Informativi Sul Web A.A. 2021/2022.




## Tecnologie utilizzate

- HTML & CSS
- Bootstrap
- Spring Boot
- Spring Security
- PostgreSQL
- OAuth 2 (Google, Facebook)
- AWS (SES, S3)
- JPA

*ancora da implementare


## Deploy su Heroku

Per visitare il sito su Heroku

```bash
  http://r3tour.herokuapp.com
```


## Il progetto

**R3 Tour** è un'agenzia di viaggi composta da un team specializzato.
L'agenzia utilizza una web-app per la gestione dei pacchetti offerti, in collaborazione con altre agenzie,
e quelli personali.
Il sistema **R3 Tour** consente di gestire e rappresentare:
- Un generico _utente_ che può registrarsi indicando i propri dati personali
- Le _destinazioni_ indicate da un nome, da una nazione e da un continente,
- I _referenti_ rappresentati da un nome, un cognome, un numero di telefono e una e-mail. Un referente è un dipendente dell'agenzia viaggi e gestisce uno o più pacchetti
- I _pacchetti_ rappresentati da un nome, una descrizione che indica da cosa è composto il pacchetto, un prezzo in €, il numero dei giorni e delle notti e infine una destinazione e un referente.

In generale possiamo dire che un pacchetto si riferisce ad una sola destinazione ed è gestito da un solo referente, una destinazione può avere più pacchetti dedicati e un referente può gestire uno o più pacchetti. Inoltre un utente generico può prenotare uno o più pacchetti.

In questo sistema possiamo avere due attori: **utente generico** e **amministratore**.

L'**amministratore** del Sistema effettua il login inserendo le proprie credenziali rappresentate
da username e password. Una volta effettuato il login, può recarsi alla _dashboard_ ed è in grado di effettuare le seguenti operazioni:
- Aggiunta, modifica ed eliminazione di una destinazione
- Aggiunta, modifica ed eliminazione di un referente
- Aggiunta, modifica ed eliminazione di un pacchetto
- Visualizzazione su tabella di tutte le destinazioni, di tutti i referenti e di tutti i pacchetti salvati
- Eliminazione degli utenti registrati

L'**utente generico** può avere due stati: autenticato e non autenticato. Nel caso in caso non fosse autenticato l'utente è in grado di visualizzare liberamente le informazioni presenti all'interno del Sistema, nello specifico può
verificare le informazioni che riguardano un determinato pacchetto e il suo relativo referente.
L'utente può essere autenticato una volta che si è registrato inserendo i propri dati e dopo aver confermato la registrazione per e-mail.
L'utente autenticato è in grado di prenotare il pacchetto desiderato.

In generale, un utente effettua le seguenti operazioni:
- Registrazione attraverso form specifico inserendo i dati personali
- Conferma registrazione attraverso e-mail
- Autenticazione attraverso form specifico inserendo username e password
- Visualizzazione e modifica dei propri dati personali
- Visualizzazione e prenotazione del pacchetto
- Visualizzazione dei dati del referente del pacchetto
- Visualizzazione di tutte le destinazioni e ricerca per destinazione






## Class Diagram

 ![cd](https://i.postimg.cc/MTGSgcVd/class-diagram.png)
## Autori

- [@danchc](https://www.github.com/danchc)

