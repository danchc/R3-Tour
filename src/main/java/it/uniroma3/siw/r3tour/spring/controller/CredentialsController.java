package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.model.Credentials;
import it.uniroma3.siw.r3tour.spring.model.Pacchetto;
import it.uniroma3.siw.r3tour.spring.model.User;
import it.uniroma3.siw.r3tour.spring.service.CredentialsService;
import it.uniroma3.siw.r3tour.spring.service.PacchettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CredentialsController {

    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected PacchettoService pacchettoService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    /**
     * Il metodo gestisce il reindirizzamento alla pagina della gestione dell'account di un utente.
     * @param httpSession
     * @return account.html
     */
    @GetMapping("/account")
    public String getUserAccountPage(HttpSession httpSession, Model model){
        httpSession.setAttribute("role", this.credentialsService.getCredentialsAuthenticated().getRuolo());
        model.addAttribute("credentials", this.credentialsService.getCredentialsAuthenticated());
        model.addAttribute("user", this.credentialsService.getCredentialsAuthenticated().getUser());
        return "account";
    }

    /**
     * Con questo metodo viene eliminato un determinato utente in base all'id passato come parametro.
     * @param id
     * @param redirectAttributes, serve per comunicare un messaggio a fine operazione
     * @return dashboard
     */
    @GetMapping("/delete/utente/{id}")
    public String deleteUser(@PathVariable("id") Long id,
                             RedirectAttributes redirectAttributes){
        Credentials credentials = this.credentialsService.findCredentialsById(id);
        this.credentialsService.deleteCredentials(credentials);
        redirectAttributes.addFlashAttribute("successmsg",
                "L'utente è stato eliminato con successo!");
        return "redirect:/dashboard";
    }

    /**
     * Il metodo viene utilizzato per il reindirizzamento alla pagina della modifica dei dati dell'utente.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/update/utente/{id}")
    public String getUpdateUtente(@PathVariable("id") Long id, Model model){
        Credentials credentials = this.credentialsService.findCredentialsById(id);
        model.addAttribute("credentials", credentials);
        model.addAttribute("user", credentials.getUser());
        return "account-update";
    }

    /**
     * Questo metodo gestisce il reindirizzamento dell'utente alla pagina per la modifica della sua password.
     * @param id
     * @param model
     * @return account-psw-update.html
     */
    @GetMapping("/update/password/utente/{id}")
    public String getUpdatePasswordUtente(@PathVariable("id") Long id, Model model) {
        Credentials credentials = this.credentialsService.findCredentialsById(id);
        model.addAttribute("credentials", credentials);
        model.addAttribute("user", credentials.getUser());
        return "account-psw-update";
    }

    @PostMapping("/update/password/utente")
    public String updatePasswordUtente(@ModelAttribute("credentials") Credentials credentials,
                                       @ModelAttribute("user") User user,
                                       BindingResult bindingResult, String confirmpsw, String currentpsw,
                                       RedirectAttributes redirectAttributes) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if(passwordEncoder.matches(currentpsw, credentials.getPassword())) {
           System.out.println("same");
        } else {
            bindingResult.reject("credentials.psw.not.matches");
            return "account-psw-update";

        }

        redirectAttributes.addFlashAttribute("successmsg", "La password è stata modificata con successo!");
        return "redirect:/account";
    }



    /**
     * Il metodo viene utilizzato per inviare i dati al server ed aggiornare l'entità nel database.
     * @param credentials
     * @param user
     * @param bindingResult
     * @param redirectAttributes
     * @return se non ci sono errori torna alla pagina dell'account
     */
    @PostMapping("/update/utente")
    public String updateUtente(@Valid @ModelAttribute("credentials") Credentials credentials,
                               @Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            return "account-update";
        }

        credentials.setUser(user);
        this.credentialsService.update(credentials);
        redirectAttributes.addFlashAttribute("successmsg", "Aggiornato con successo!");
        return "redirect:/account";
    }

    /**
     * Il metodo viene utilizzato per reindirizzare l'utente alla pagina dei suoi pacchetti acquistati.
     * @param model
     * @return utente/pacchetti.html
     */
    @GetMapping("/utente/pacchetti")
    public String getPacchettiPrenotati(Model model){
        model.addAttribute("user", this.credentialsService.getCredentialsAuthenticated().getUser());
        model.addAttribute("credentials", this.credentialsService.getCredentialsAuthenticated());
        return "user/pacchetti";
    }

    /**
     * Il metodo viene utilizzato per gestire la prenotazione una volta effettuato il pagamento.
     * @param id
     * @param redirectAttributes
     * @return la pagina del pacchetto con un messaggio di prenotazione effettuata
     */
    @GetMapping("/success/pacchetto/{id}")
    public String addPacchettoToUser(@PathVariable("id") Long id,
                                     RedirectAttributes redirectAttributes) {
        Pacchetto pacchettoPrenotato = this.pacchettoService.findPacchettoById(id);
        Credentials credentials = this.credentialsService.getCredentialsAuthenticated();
        User user = credentials.getUser();
        List<Pacchetto> prenotazioni = user.getPacchetti();

        //se non ci sono prenotazioni aggiungi
        if(prenotazioni.isEmpty()) {
            prenotazioni.add(pacchettoPrenotato);
        } else if(prenotazioni.contains(pacchettoPrenotato)){
            //già ha prenotato questo pacchetto, non può aggiungerlo
        } else {
            //se è diverso e ci sono altre prenotazioni allora aggiungi
            prenotazioni.add(pacchettoPrenotato);
        }

        this.credentialsService.update(credentials);
        redirectAttributes.addFlashAttribute("successmsg", "Complimenti! La prenotazione del pacchetto "
        + pacchettoPrenotato.getNome() + " è stata effettuata con successo. Buon viaggio!");
        return "redirect:/pacchetto/{id}";
    }


}
