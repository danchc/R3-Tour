package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.controller.validator.PacchettoValidator;
import it.uniroma3.siw.r3tour.spring.model.Credentials;
import it.uniroma3.siw.r3tour.spring.model.Pacchetto;
import it.uniroma3.siw.r3tour.spring.model.Referente;
import it.uniroma3.siw.r3tour.spring.service.CredentialsService;
import it.uniroma3.siw.r3tour.spring.service.DestinazioneService;
import it.uniroma3.siw.r3tour.spring.service.PacchettoService;
import it.uniroma3.siw.r3tour.spring.service.ReferenteService;
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
import java.util.List;

@Controller
public class PacchettoController {

    @Autowired
    protected PacchettoService pacchettoService;

    @Autowired
    protected DestinazioneService destinazioneService;

    @Autowired
    protected ReferenteService referenteService;

    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected PacchettoValidator pacchettoValidator;

    /**
     * Questo metodo ci reindirizza alla pagina dei pacchetti con la lista dei
     * pacchetti salvati nel database.
     * @param model
     * @return pacchetti.html
     */
    @GetMapping("/pacchetti")
    public String getPagePacchetti(Model model){
        model.addAttribute("pacchetti",
                this.pacchettoService.findAllPacchetti());

        return "pacchetti";
    }

    /**
     * Il metodo gestisce il reindirizzamento alla pagina per l'aggiunta di un nuovo pacchetto.
     * @param model
     * @return cp-pacchetti.html
     */
    @GetMapping("/add/new/pacchetto")
    public String getFormPacchetto(Model model) {
        model.addAttribute("pacchetti", this.pacchettoService.findAllPacchetti());
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        model.addAttribute("pacchetto", new Pacchetto());
        return "admin/cp-pacchetti";
    }

    /**
     * Il metodo viene utilizzato per aggiungere un nuovo pacchetto nel database.
     * @param pacchetto
     * @param bindingResult
     * @param redirectAttributes
     * @param model
     * @return dashboard se non ci sono errori, cp-pacchetti.html altrimenti
     */
    @PostMapping("/new/pacchetto")
    public String addNewPacchetto(@Valid @ModelAttribute("pacchetto") Pacchetto pacchetto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, Model model){

        /* controllo se ci sono errori */
        this.pacchettoValidator.validate(pacchetto, bindingResult);

        if(!bindingResult.hasErrors()){
            this.pacchettoService.inserisci(pacchetto);
            redirectAttributes.addFlashAttribute("successmsg",
                    "Il pacchetto è stato aggiunto correttamente.");
            return "redirect:/dashboard";
        }

        model.addAttribute("pacchetti", this.pacchettoService.findAllPacchetti());
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        return "admin/cp-pacchetti";
    }

    /**
     * Il metodo viene utilizzato per eliminare un determinato pacchetto dal database.
     * @param id del pacchetto da eliminare
     * @param redirectAttributes
     * @return dashboard
     */
    @GetMapping("/delete/pacchetto/{id}")
    public String deletePacchetto(@PathVariable("id") Long id,
                                  RedirectAttributes redirectAttributes){
        Pacchetto pacchetto = this.pacchettoService.findPacchettoById(id);
        for(Credentials credentials : this.credentialsService.findAllUtentiRegistrati()){
            if(credentials.getUser().getPacchetti().contains(pacchetto)){
                credentials.getUser().getPacchetti().remove(pacchetto);
            }
        }
        this.pacchettoService.deletePacchetto(pacchetto);
        redirectAttributes.addFlashAttribute("successmsg",
                "Il pacchetto " + pacchetto.getNome() + " è stato eliminato con successo!");
        return "redirect:/dashboard";
    }

    /**
     * Il metodo gestisce il reindirizzamento alla pagina per la modifica del pacchetto.
     * @param id
     * @param model
     * @return admin/cp-pacchetti-update.html
     */
    @GetMapping("/update/pacchetto/{id}")
    public String getUpdatePacchetto(@PathVariable("id") Long id,
                                     Model model){
        model.addAttribute("pacchetto", this.pacchettoService.findPacchettoById(id));
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        model.addAttribute("pacchetti", this.pacchettoService.findAllPacchetti());
        return "admin/cp-pacchetto-update";
    }

    /**
     * Il metodo viene utilizzato per modificare le informazioni che riguardano un pacchetto esistente.
     * @param pacchetto
     * @param bindingResult
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping("/update/pacchetto")
    public String updatePacchetto(@Valid @ModelAttribute("pacchetto") Pacchetto pacchetto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, Model model){
        /* controllo se ci sono errori */
        this.pacchettoValidator.validate(pacchetto, bindingResult);

        if(!bindingResult.hasErrors()){
            this.pacchettoService.inserisci(pacchetto);
            redirectAttributes.addFlashAttribute("successmsg",
                    "Il pacchetto " + pacchetto.getNome()
                            + " è stato aggiornato con successo!");
            return "redirect:/dashboard";
        }

        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        model.addAttribute("pacchetti", this.pacchettoService.findAllPacchetti());
        return "admin/cp-pacchetti-update";
    }

    /**
     * Questo metodo viene utilizzato per reindirizzare l'utente alla pagina dove sono
     * presenti tutte le caratteristiche di un determinato pacchetto.
     * @param id
     * @param model
     * @return pacchetto.html
     */
    @GetMapping("/pacchetto/{id}")
    public String getPaginaPacchetto(@PathVariable("id") Long id,
                                     Model model, HttpSession httpSession) {

        Pacchetto pacchetto = this.pacchettoService.findPacchettoById(id);
        model.addAttribute("pacchetto", pacchetto);
        httpSession.setAttribute("role", this.credentialsService.getCredentialsAuthenticated().getRuolo());
        return "pacchetto";
    }


    /**
     * Questo metodo viene utilizzato per visualizzare tutti i pacchetti in base al nome
     * della destinazione.
     * @param nome
     * @param model
     * @return pacchetti.html (raggruppati per nome)
     */
    @GetMapping("/pacchetti/{nome}")
    public String getPacchettiDaDestinazione(@PathVariable("nome") String nome,
                                             Model model){
        List<Pacchetto> pacchetti = this.pacchettoService.findPacchettiByDestinazione(nome);
        model.addAttribute("pacchetti", pacchetti);
        return "pacchetti";
    }

    /**
     * Il metodo viene utilizzato per il reindirizzamento alla pagina della prenotazione (pagamento) del pacchetto.
     * @param id
     * @param model
     * @return checkout.html
     */
    @GetMapping("/checkout/pacchetto/{id}")
    public String getPreOrderPage(@PathVariable("id") Long id,
                                  Model model, RedirectAttributes redirectAttributes){

        Pacchetto pacchetto = this.pacchettoService.findPacchettoById(id);
        Credentials loggedUser = this.credentialsService.getCredentialsAuthenticated();

        //se l'utente ha già prenotato non va nella schermata del checkout
        if(loggedUser.getUser().getPacchetti().contains(pacchetto)){
            redirectAttributes.addFlashAttribute("errormsg", "Attenzione! Non è possibile prenotare il pacchetto "
            + pacchetto.getNome() + " perché è già presente una prenotazione nel sistema.");
            return "redirect:/pacchetto/{id}";
        }

        model.addAttribute("pacchetto", pacchetto);
        return "checkout";

    }
}
