package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.model.Credentials;
import it.uniroma3.siw.r3tour.spring.model.User;
import it.uniroma3.siw.r3tour.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class CredentialsController {

    @Autowired
    protected CredentialsService credentialsService;

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


}
