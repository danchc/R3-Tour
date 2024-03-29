package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.controller.validator.DestinazioneValidator;
import it.uniroma3.siw.r3tour.spring.model.Destinazione;
import it.uniroma3.siw.r3tour.spring.service.ContinenteService;
import it.uniroma3.siw.r3tour.spring.service.DestinazioneService;
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

import javax.validation.Valid;

@Controller
public class DestinazioneController {

    @Autowired
    protected DestinazioneService destinazioneService;

    @Autowired
    protected ContinenteService continenteService;

    @Autowired
    protected ReferenteService referenteService;

    @Autowired
    protected DestinazioneValidator destinazioneValidator;

    /**
     * Il metodo viene utilizzato per il reindirizzamento alla pagina di tutte le destinazioni.
     * @param model
     * @return destinazioni.html
     */
    @GetMapping("/destinazioni")
    public String getPageDestinazioni(Model model) {
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("continenti", this.continenteService.findAllContinenti());
        return "destinazioni";
    }

    /**
     * Il metodo viene utilizzato per il reindirizzamento alla pagina del form per l'aggiunta di una nuova destinazione.
     * @param model
     * @return admin/cp-destinazioni.html
     */
    @GetMapping("/add/new/destinazione")
    public String getFormDestinazione(Model model){
        model.addAttribute("continenti", this.continenteService.findAllContinenti());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("destinazione", new Destinazione());
        return "admin/cp-destinazioni";
    }

    /**
     * Il metodo viene utilizzato per inviare al server i dati della nuova destinazione da aggiungere all'interno
     * del database.
     * @param redirectAttributes
     * @param destinazione
     * @param bindingResult
     * @return dashboard se non ci sono errori, admin/cp-destinazioni.html altrimenti
     */
    @PostMapping("/new/destinazione")
    public String addNewDestinazione(RedirectAttributes redirectAttributes,
                                     @Valid @ModelAttribute("destinazione") Destinazione destinazione,
                                     BindingResult bindingResult,
                                     Model model){

        /* controllo se ci sono errori */
        this.destinazioneValidator.validate(destinazione, bindingResult);

        if(!bindingResult.hasErrors()){
            this.destinazioneService.inserisci(destinazione);
            redirectAttributes.addFlashAttribute("successmsg", "La destinazione " +
                    destinazione.getNome() + " è stata aggiunta con successo!");
            return "redirect:/dashboard";
        }

        model.addAttribute("continenti", this.continenteService.findAllContinenti());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        return "admin/cp-destinazioni";
    }


    /**
     * Il metodo viene utilizzato per eliminare una certa destinazione dal database.
     * @param id destinazione da eliminare
     * @param redirectAttributes
     * @return dashboard
     */
    @GetMapping("/delete/destinazione/{id}")
    public String deleteDestinazione(@PathVariable("id") Long id,
                                     RedirectAttributes redirectAttributes){
        Destinazione destinazione = this.destinazioneService.findDestinazioneById(id);
        this.destinazioneService.deleteDestinazione(destinazione);
        redirectAttributes.addFlashAttribute("successmsg", "La destinazione "
                + destinazione.getNome() + " è stata eliminata con successo!");
        return "redirect:/dashboard";
    }

    /**
     * Il metodo gestisce il reindirizzamento alla pagina per la modifica della destinazione.
     * @param id
     * @param model
     * @return cp-destinazioni-update.html
     */
    @GetMapping("/update/destinazione/{id}")
    public String getUpdateDestinazione(@PathVariable("id") Long id, Model model) {
        Destinazione destinazione = this.destinazioneService.findDestinazioneById(id);
        model.addAttribute("destinazione", destinazione);
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("continenti", this.continenteService.findAllContinenti());
        return "admin/cp-destinazioni-update";
    }

    /**
     * Il metodo gestisce l'aggiornamento dei dati di una determinata destinazione all'interno del
     * database.
     * @param destinazione
     * @param bindingResult
     * @param redirectAttributes
     * @param model
     * @return se non ci sono errori la dashboard altrimenti il form
     */
    @PostMapping("/update/destinazione")
    public String updateDestinazione(@Valid @ModelAttribute("destinazione") Destinazione destinazione,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, Model model){

        /* controllo se ci sono errori */
        this.destinazioneValidator.validate(destinazione, bindingResult);

        //se non ci sono errori
        if(!bindingResult.hasErrors()){
            this.destinazioneService.inserisci(destinazione);
            redirectAttributes.addFlashAttribute("successmsg", "La destinazione " +
                    destinazione.getNome() + " è stata aggiornata con successo!");
            return "redirect:/dashboard";
        }

        //se ci sono errori
        model.addAttribute("continenti", this.continenteService.findAllContinenti());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        return "admin/cp-destinazioni-update";
    }
}
