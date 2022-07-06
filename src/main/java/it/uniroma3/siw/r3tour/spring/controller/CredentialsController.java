package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.model.Credentials;
import it.uniroma3.siw.r3tour.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String getUserAccountPage(HttpSession httpSession){
        httpSession.setAttribute("role", this.credentialsService.getCredentialsAuthenticated().getRuolo());
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


}
