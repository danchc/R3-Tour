package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.model.Credentials;
import it.uniroma3.siw.r3tour.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

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
        return "account-update";
    }


}
